import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Demo04Filter {
    Connection conn = null;
    Admin admin = null;
    Table students = null;
    Scan scan = null;
    TableName name = TableName.valueOf("students");

    // 将字符数组转成string
    public String bytesToSting(byte[] b) {
        String s = Bytes.toString(b);
        return s;
    }


    public void useFilterAndPrint(Filter filter) throws IOException {

        scan = new Scan();
        scan.setFilter(filter);
        students = conn.getTable(name);
        ResultScanner scanner = students.getScanner(scan);
        for (Result result : scanner) {
            byte[] row = result.getRow();
            String rowkey = Bytes.toString(row);
            System.out.printf("rowkey:" + rowkey);
            List<Cell> cells = result.listCells();

            for (Cell cell : cells) {
//            byte[] bytes = CellUtil.cloneValue(cell);
                byte[] bytes = cell.getValue();
                String value = Bytes.toString(bytes);
                System.out.printf(",value:" + value);
            }
            System.out.println();
        }
    }

    //加载配置文件
    @Before
    public void init() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();
    }


    // 通过RowFilter过滤比rowKey  1500100010 小的所有
    @Test
    public void BinaryComparatorFilter() throws IOException {
        BinaryComparator comparator = new BinaryComparator("1500100010".getBytes());
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, comparator);
        students = conn.getTable(name);

        Scan scan = new Scan();
        scan.setFilter(rowFilter);
        ResultScanner scanner = students.getScanner(scan);

        for (Result result : scanner) {
            byte[] row = result.getRow();
            String rowkey = Bytes.toString(row);
            System.out.printf("rowkey:" + rowkey);
            List<Cell> cells = result.listCells();

            for (Cell cell : cells) {
                byte[] bytes = CellUtil.cloneValue(cell);
                String value = Bytes.toString(bytes);
                System.out.printf(",value:" + value);
            }
            System.out.println();
        }

    }


    //    通过FamilyFilter查询列簇名包含in的所有列簇下面的数据
    @Test
    public void SubstringComparatorFilter() throws IOException {


        SubstringComparator in = new SubstringComparator("inf");
        FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, in);

        Scan scan = new Scan();
        scan.setFilter(familyFilter);
        students = conn.getTable(name);
        ResultScanner scanner = students.getScanner(scan);

        for (Result result : scanner) {
            byte[] row = result.getRow();
            String rowkey = Bytes.toString(row);
            System.out.printf("rowkey:" + rowkey);
            List<Cell> cells = result.listCells();

            for (Cell cell : cells) {
//            byte[] bytes = CellUtil.cloneValue(cell);
                byte[] bytes = cell.getValue();
                String value = Bytes.toString(bytes);
                System.out.printf(",value:" + value);
            }
            System.out.println();
        }


    }


    // 查询列名包含a的列 下面的所有的值
    // QualifierFilter SubstringComparator
    @Test
    public void QualifierFilter() throws IOException {
        QualifierFilter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("name"));

        useFilterAndPrint(filter);

    }


    // 过滤文科一班的学生 并且返回学生所有信息
    // ValueFilter是作用与每一个cells上的，只有符合条件的cell才会保留
    @Test
    public void BinaryComparatorValueFilter() throws IOException {
        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator("理科二班".getBytes()));
        useFilterAndPrint(valueFilter);

    }

    // 过滤文科一班的学生 并且返回学生的所有的信息
    // 会完整地返回整条数据
    // 在比较地时候需要指定 列簇和列名，如果数据中存在没有所指定的列簇的数据 则会保留并返回
    // 在比较地时候还需要指定 列簇和列名，如果数据中存在没有所指定的列名的数据 则也会保留并返回
    @Test
    public void SingleColumnValueFilterClazz() throws IOException {
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
                "info".getBytes()
                , "clazz".getBytes()
                , CompareFilter.CompareOp.EQUAL
                , "文科一班".getBytes());
        useFilterAndPrint(singleColumnValueFilter);
    }

    // 相对于SingleColumnValueFilter 会将用于过滤的列值去除 并返回
    @Test
    public void SingleColumnValueExcludeFilterClazz() throws IOException {
        SingleColumnValueExcludeFilter singleColumnValueExcludeFilter = new SingleColumnValueExcludeFilter(
                "info".getBytes()
                , "clazz".getBytes()
                , CompareFilter.CompareOp.EQUAL
                , "文科一班".getBytes());
        useFilterAndPrint(singleColumnValueExcludeFilter);
    }
}

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Demo02API {
    Connection conn = null;
    Admin admin = null;

    //加载配置文件
    @Before
    public void init() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();

    }

    //创建表

    @Test
    public void CreateTable() throws IOException {
        TableName test1 = TableName.valueOf("test1");
        if (admin.tableExists(test1)) {
            admin.disableTable(test1);
            admin.deleteTable(test1);
        }

        //  初始化表
        HTableDescriptor hTableDescriptor = new HTableDescriptor(test1);
        HColumnDescriptor cf1 = new HColumnDescriptor("cf1");
        cf1.setMaxVersions(5);
        hTableDescriptor.addFamily(cf1);

        //创建表
        admin.createTable(hTableDescriptor);
        System.out.println("创建成功！");
    }

    // 删除表
    @Test
    public void deletetable() throws IOException {
        TableName test1 = TableName.valueOf("students");
        if (!admin.tableExists(test1)) {

            System.out.printf("表不存在");
            return;
        }
        admin.disableTable(test1);
        admin.deleteTable(test1);

        System.out.printf("删除完成");

    }


    //列出表
    @Test
    public void listtable() throws IOException {
        HTableDescriptor[] tables = admin.listTables();
        for (HTableDescriptor table : tables) {
            String nameAsString = table.getNameAsString();
            System.out.println(nameAsString);
        }

    }

    @Test
    public void modifyTable() throws IOException {
        TableName test1 = TableName.valueOf("test1");
        //使用admin对象对表进行修改  说白一点是修改，倒不如是覆盖  如果直接new的方式创建 TableDescriptor对象，根本就获取不了原来的表结构
        // 而是有创建了一个
        HTableDescriptor tableDescriptor = admin.getTableDescriptor(test1);
        //添加一个列簇
        tableDescriptor.addFamily(new HColumnDescriptor("cf2"));

        //正式修改
        admin.modifyTable(test1, tableDescriptor);

    }

    @Test
    public void put() throws IOException {
        // 因为hbase在读写数据的时候，是从zookeeper中获取元数据的。所以要用conn对象去获取表
        // conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        //conn = ConnectionFactory.createConnection(conf);

        // 获得表对象
        Table test1 = conn.getTable(TableName.valueOf("test1"));
        Put put = new Put("003".getBytes());
        put.addColumn("cf1".getBytes(), "name".getBytes(), "wangwu".getBytes());
        put.addColumn("cf2".getBytes(), "clazz".getBytes(), "文科二班".getBytes());
        test1.put(put);
        System.out.println("put 数据成功！");
    }

    @Test

    // 将学生数据导入hbase
    public void putall() throws IOException {
        // 创建学生表

        TableName students = TableName.valueOf("students");
        if (admin.tableExists(students)) {
            System.out.println("表已经存在");
            return;
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(students);
        hTableDescriptor.addFamily(new HColumnDescriptor("info"));

        admin.createTable(hTableDescriptor);

        Table table = conn.getTable(students);
        // 读取文件
        BufferedReader br = new BufferedReader(new FileReader("data/students.txt"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            //1500100001,施笑槐,22,女,文科六班
            String id = split[0];
            String name = split[1];
            String age = split[2];
            String gender = split[3];
            String clazz = split[4];

            Put put = new Put(id.getBytes());

            put.addColumn("info".getBytes(), "name".getBytes(), name.getBytes());
            put.addColumn("info".getBytes(), "age".getBytes(), age.getBytes());
            put.addColumn("info".getBytes(), "gender".getBytes(), gender.getBytes());
            put.addColumn("info".getBytes(), "clazz".getBytes(), clazz.getBytes());
            table.put(put);

        }

        System.out.printf("插入成功！");

    }


    @Test
    //get数据
    public void get() throws IOException {
        // 得到表对象

        Table students = conn.getTable(TableName.valueOf("test1"));
        Get get = new Get("003".getBytes());

        Result result = students.get(get);

        byte[] cf1 = "cf1".getBytes();
        byte[] cf2 = "cf2".getBytes();

        String name = Bytes.toString(result.getValue(cf1, "name".getBytes()));
        String age = Bytes.toString(result.getValue(cf1, "age".getBytes()));
        String gender = Bytes.toString(result.getValue(cf1, "gender".getBytes()));
        String clazz = Bytes.toString(result.getValue(cf2, "clazz".getBytes()));
        System.out.printf("name:" + name + ", age:" + age + ",gender:" + gender + ",clazz:" + clazz);

    }

    @Test
    public void scan() throws IOException {
        Table students = conn.getTable(TableName.valueOf("students"));

        Scan scan = new Scan();
        scan.withStartRow("1500100001".getBytes());
        scan.withStopRow("1500100010".getBytes());
        ResultScanner results = students.getScanner(scan);
        for (Result result : results) {
            String rowkey = Bytes.toString(result.getRow());


            String name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()));
            String age = Bytes.toString(result.getValue("info".getBytes(), "age".getBytes()));
            String gender = Bytes.toString(result.getValue("info".getBytes(), "gender".getBytes()));
            String clazz = Bytes.toString(result.getValue("info".getBytes(), "clazz".getBytes()));

            System.out.printf("id:" + rowkey + ",name:" + name + ",age:" + age +
                    ",gender:" + gender + ",clazz:" + clazz);
            System.out.println();


        }


    }

    // 使用cellUtil获取数据
    @Test
    public void cellUtil() throws IOException {
        Table students = conn.getTable(TableName.valueOf("students"));
        Scan scan = new Scan();

        scan.withStartRow("1500100001".getBytes());
        scan.withStopRow("1500100011".getBytes());

        ResultScanner scanner = students.getScanner(scan);
        for (Result result : scanner) {
            // 获取当前的rowkey对应的所有的cell
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                byte[] qualifier = CellUtil.cloneQualifier(cell);
                String qf = Bytes.toString(qualifier);

                String value = Bytes.toString(CellUtil.cloneValue(cell));

                System.out.println(rowkey + "  " + cf + "   " + qf + ":" + value);

            }
            System.out.println();


        }


    }

    @Test
    public void getall() throws IOException {

        Table students = conn.getTable(TableName.valueOf("students"));

        Scan scan = new Scan();

        ResultScanner scanner = students.getScanner(scan);

        for (Result result : scanner) {
            byte[] row = result.getRow();
            String rowkey = Bytes.toString(row);
            System.out.printf("id:" + rowkey);

            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                System.out.printf("," + Bytes.toString(CellUtil.cloneValue(cell)) + " ");
            }

            System.out.println();


        }


    }


    @After
    public void close() throws IOException {

        admin.close();
        conn.close();

    }


}

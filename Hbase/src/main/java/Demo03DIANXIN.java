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
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 加载电信数据到hbase中，使用Hbase中提供的多版本性
 * 根据mdn获取最新的10个位置记录
 */

public class Demo03DIANXIN {
    Connection conn = null;
    Admin admin = null;
    Table dianxin = null;

    TableName name = TableName.valueOf("dianxin");

    //加载配置文件
    @Before
    public void init() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();


    }


    // 创建表

    @Test
    public void createTable() throws IOException {

        HTableDescriptor hTableDescriptor = new HTableDescriptor(name);

        HTableDescriptor cf1 = hTableDescriptor.addFamily(new HColumnDescriptor("cf1").setMaxVersions(10));

        admin.createTable(cf1);

        System.out.println("创建成功！！" + name);
    }


    @Test

    public void Loaddata() throws IOException {
        Table table = conn.getTable(name);


        BufferedReader br = new BufferedReader(new FileReader("data/DIANXIN.csv"));
        String line = null;


        while ((line = br.readLine()) != null) {
            String[] splits = line.split(",");

            String mdn = splits[0];
            long start_time = Long.parseLong(splits[1]);
            String longitude = splits[4];
            String latitude = splits[5];
            String location = longitude + "," + latitude;

            Put put = new Put(mdn.getBytes());

            put.addColumn("cf1".getBytes(), "location".getBytes(), start_time, location.getBytes());


            table.put(put);


        }

        System.out.println("插入成功" + dianxin);

        System.exit(10);
    }


    @Test
    public void getLast10loc() throws IOException {
        String rowkey = "6A1F2758502F63D20E47D9359E8628255E531363";
        Get get = new Get(rowkey.getBytes());
        dianxin = conn.getTable(name);

        get.setMaxVersions(10);

        Result result = dianxin.get(get);
        List<Cell> cells = result.listCells();

        for (Cell cell : cells) {
            byte[] bytes = CellUtil.cloneValue(cell);
            String loc = Bytes.toString(bytes);
            System.out.printf(rowkey + "," + loc);
            System.out.println();
        }


    }


    @After
    public void close() throws IOException {
        admin.close();
        conn.close();

    }
}

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class Demo1Test {
    // 使用JavaAPI创建表
    public static void main(String[] args) throws IOException {
        // 创建配置对象
        Configuration conf = HBaseConfiguration.create();
        // 指定zk集群的地址
        conf.set("hbase.zookeeper.quorum", "master:2181,node1:2181,node2:2181");

        // 先创建与HBase的连接
        Connection conn = ConnectionFactory.createConnection(conf);

        // 创建一个Admin对象
        // 通过Admin对象就可以对表进行操作
        Admin admin = conn.getAdmin();

        // 创建HTableDescriptor对象
        // 通过HTableDescriptor对象可以对列簇进行操作
        HTableDescriptor testJavaAPI = new HTableDescriptor(TableName.valueOf("testJavaAPI"));

        // 创建HColumnDescriptor对象
        // 通过HColumnDescriptor对象可以对列簇进行一些配置
        HColumnDescriptor cf1 = new HColumnDescriptor("cf1");
        // 设置版本号
        cf1.setMaxVersions(5);
        // 设置存活时间
        cf1.setTimeToLive(5);


        // 添加列簇
        testJavaAPI.addFamily(cf1);
        // 创建表
        admin.createTable(testJavaAPI);

        // 关闭链接
        admin.close();
        conn.close();

    }
}

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.RegionLocator;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.KeyValueSortReducer;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.mapreduce.SimpleTotalOrderPartitioner;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.atomic.LongAccumulator;

public class Demo07BulkLoading {

    public  static  class Mymapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue>{
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue>.Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split(",");

            String mdn = splits[0];
            String start_time = splits[1];
            String rowk= mdn+"_"+start_time;
            String lg = splits[4];
            String lat = splits[5];
            String end_time = splits[2];


            String location = lg + "," + lat;


            KeyValue keyValue = new KeyValue(rowk.getBytes(),
                    "cf1".getBytes(),
                    "location".getBytes(),
                    location.getBytes());

            KeyValue keyValue1 = new KeyValue(rowk.getBytes(), Long.parseLong(end_time));

            context.write(new ImmutableBytesWritable(rowk.getBytes()),
                    keyValue);
            context.write(new ImmutableBytesWritable(rowk.getBytes()),
                    keyValue1);

        }
    }


    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        Job job = Job.getInstance(conf);
        job.setJobName("Demo07BulkLoading");
        job.setJarByClass(Demo07BulkLoading.class);


        job.setMapperClass(Demo07BulkLoading.Mymapper.class);

        job.setMapOutputValueClass(KeyValue.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);


        job.setNumReduceTasks(4);

        // 保证全局有序

        job.setPartitionerClass(SimpleTotalOrderPartitioner.class);


        // 会将进入reduce中的数据进行排序以符合HBase的要求
        job.setReducerClass(KeyValueSortReducer.class);

        FileInputFormat.addInputPath(job, new Path("/dianxin1"));
        FileOutputFormat.setOutputPath(job, new Path("/data/hfile"));


        // 对MR的输出按照HBase的要求进行格式化..
        Connection conn = ConnectionFactory.createConnection(conf);
        Table bulk_load = conn.getTable(TableName.valueOf("bulk_load"));
        RegionLocator regionLocator = conn.getRegionLocator(TableName.valueOf("bulk_load"));


        HFileOutputFormat2.configureIncrementalLoad(job, bulk_load,regionLocator);

        // 提交任务
        job.waitForCompletion(true);

        LoadIncrementalHFiles hFiles = new LoadIncrementalHFiles(conf);
        hFiles.doBulkLoad(new Path("/data/hfile"),conn.getAdmin(),bulk_load,regionLocator);

    }

}

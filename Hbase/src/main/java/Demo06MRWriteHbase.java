import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class Demo06MRWriteHbase {

    // 读取本地score.txt文件 计算每个学生的总分 并将结果写入HBase

    public  static  class MyMapper extends Mapper<LongWritable, Text,Text, IntWritable>{
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split(",");

            String id = splits[0];
            int score = Integer.parseInt(splits[2]);

            context.write(new Text(id),new IntWritable(score));
        }
    }



    public static class Myreducer extends TableReducer<Text,IntWritable, NullWritable>{

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, NullWritable, Mutation>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                int i = value.get();
                sum+=i;
            }
            Put put = new Put(key.getBytes());

            put.addColumn("cf1".getBytes(),"sum_score".getBytes(),(String.valueOf(sum)).getBytes());

            context.write(NullWritable.get(),put);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");
        Job job = Job.getInstance(conf);
        job.setJobName("Demo6MRWriteHBase");
        job.setJarByClass(Demo06MRWriteHbase.class);

        // 配置Map任务
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置reduce任务
        TableMapReduceUtil.initTableReducerJob("sum_score",Myreducer.class,job);


        FileInputFormat.addInputPath(job,new Path("/data"));

        job.waitForCompletion(true);






    }


}

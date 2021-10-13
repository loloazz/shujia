import org.apache.commons.math.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

public class Demo05MRreadHbase {
    public static class MyMapper extends TableMapper<Text, IntWritable> {

        final static IntWritable one = new IntWritable(1);

        @Override
        protected void map(ImmutableBytesWritable key, Result value, Mapper<ImmutableBytesWritable, Result, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            byte[] row = value.getRow();

            String rowkey = Bytes.toString(row);

            byte[] clazz = value.getValue("info".getBytes(), "clazz".getBytes());

            String k = Bytes.toString(clazz);
            context.write(new Text(k), one);
        }


    }

    public static class MyReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {

            int cnt = 0;
            for (IntWritable value : values) {
                cnt++;
            }
            context.write(key, new IntWritable(cnt));
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();

        conf.set("hbase.zookeeper.quorum", "hadoop100:2181,hadoop101:2181,hadoop102:2181");

        Job job = Job.getInstance(conf);
        job.setJobName("Demo05MRreadHbase");


        job.setJarByClass(Demo05MRreadHbase.class);

        TableMapReduceUtil.initTableMapperJob(
                "students",
                new Scan()
                , MyMapper.class
                , Text.class,
                IntWritable.class,
                job
        );


        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);


        FileOutputFormat.setOutputPath(job,new Path("/mrHabse01"));

        if (job.waitForCompletion(true)){

            System.out.println("成功");
        }else {
            System.out.println("shibai");
        }

    }

}

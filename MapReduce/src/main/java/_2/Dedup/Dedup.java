package _2.Dedup;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * @Author: Yaolong
 * @Date: 2021/8/30 10:44
 * @Pagename: _2.Dedup
 * @ProjectName: shujia
 * @Describe: 用来数据的去重的
 **/


/*
 * 项目的思路：这里与wordcount的思路相似，只是在map阶段对输出为
 * key，null 的形式，而相同的key 会进入同一个reduce中，直接把value置为null 在输出即可
 * */
public class Dedup {
    // TODO: 2021/8/30  map将输入中的key复制到输出数据的key上，直接输出
    public static class Map extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    // TODO: 2021/8/30  reduce将输入中的key复制到输出数据的key上，直接输出
    public static class Reduce extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Reducer<Text, NullWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "数据的去重");

        job.setJarByClass(Dedup.class);

        job.setOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);

        //设置预聚合操作
        job.setCombinerClass(Reduce.class);

        job.setReducerClass(Reduce.class);
        job.setMapperClass(Map.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        if (b) {
            System.out.println("执行成功！");
        } else {
            System.out.println("执行失败");
        }
    }
}

package filter;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author: Yaolong
 * @Date: 2021/9/1 10:59
 * @Pagename: filter
 * @ProjectName: shujia
 * @Describe:
 **/
public class filter {
    public static class myMap extends Mapper<LongWritable, Text,Text,Text> {


        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            InputSplit inputSplit = context.getInputSplit();

            FileSplit fs = (FileSplit) inputSplit;

            Path path = fs.getPath();
            if (path.toString().contains("students")) {

                String[] split = value.toString().split(",");
//            1500100001,施笑槐,22,女,文科六班
                if ("文科六班".equals(split[4])) {
                    context.write(new Text("文科六班"), value);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(filter.class);
        job.setMapperClass(myMap.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job,new Path("F:\\shujia\\MapReduce\\data"));
        FileOutputFormat.setOutputPath(job,new Path("F:\\shujia\\MapReduce\\output\\filter"));


        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);

    }
}



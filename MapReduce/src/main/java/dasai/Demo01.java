package dasai;

import dataformat.dataformat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URISyntaxException;
import java.util.HashMap;


public class Demo01 {
    public static class MyMap extends Mapper<LongWritable, Text, Text, NullWritable> {
         HashMap<String, String> hb = null;

        @Override
        protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {


            hb = new HashMap<>();
            FileSystem fs = FileSystem.get(context.getConfiguration());

            FSDataInputStream open = fs.open(new Path("/guosai/localdata.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(open);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {

                String[] split = line.split("\t");


                System.out.println(split[2]);

                hb.put(split[0], split[2]);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            String splits = value.toString();

            String[] split = splits.split(",");

            String bizhong = split[3];
            String s = hb.get(bizhong);
            String replace = splits.replace(bizhong, s);
            System.out.println(replace);

            context.write(new Text(replace), NullWritable.get());
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Demo01.class);

        job.setJobName("dataReplace");

        job.setUser("root");
        job.setJarByClass(Demo01.class);
        job.setMapperClass(MyMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        FileInputFormat.setInputPaths(job, new Path("/guosai/daikuai.CSV"));
        FileOutputFormat.setOutputPath(job, new Path("/guosai/mr1"));
        job.waitForCompletion(true);


    }

}

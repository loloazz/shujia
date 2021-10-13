package SumScore;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

/**
 * @Author: Yaolong
 * @Date: 2021/9/2 8:41
 * @Pagename: SumScore
 * @ProjectName: shujia
 * @Describe:
 **/
public class SumScoreMR {
    public  static  class  SumScoreMap extends Mapper<LongWritable, Text,Text, IntWritable>{
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            String[] split = value.toString().split(",");

            String sno = split[0];
            int score = Integer.parseInt(split[2]);

            context.write(new Text(sno),new IntWritable(score));

        }
    }

    public  static  class SumScoreReduce extends Reducer<Text, IntWritable,Text,IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
            sum+=value.get();

            }

            context.write(key,new IntWritable(sum));



        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJobName("SumScoreMR");
        job.setJarByClass(SumScoreMR.class);


        job.setMapperClass(SumScoreMap.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setReducerClass(SumScoreReduce.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));

        FileOutputFormat.setOutputPath(job,new Path(args[1]));


        if (job.waitForCompletion(true)) {
            System.out.println("seccess！");
        }else {
            System.out.println("lose");
        }

                
    }


}

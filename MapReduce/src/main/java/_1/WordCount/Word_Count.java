package _1.WordCount;

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
 * @Date: 2021/8/30 9:08
 * @Pagename: WordCount
 * @ProjectName: shujia
 * @Describe:
 **/
public class Word_Count {

    public static class WordCountMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text words = new Text();

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            // TODO: 2021/8/30  转换成string 方便切割
            String s = value.toString();
            String[] word = s.split(" ");
            for (String s1 : word) {
                words.set(s1);
                context.write(words, one);
            }
        }
    }

    public static class IntSumReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum = sum + value.get();

            }
            result.set(sum);
            context.write(key, result);

        }

    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        // TODO: 2021/8/30 设置工作的名称，以及加载默认的配置文件
        Job job = Job.getInstance(conf, "wordCount");

        job.setJarByClass(Word_Count.class);

        job.setMapperClass(WordCountMap.class);
        job.setCombinerClass(IntSumReduce.class);
        job.setReducerClass(IntSumReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);



    }


}

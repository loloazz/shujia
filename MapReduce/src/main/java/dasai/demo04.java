package dasai;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class demo04 {

    public static class MyMap extends Mapper<LongWritable, Text, Text, Text> {

        //项目序号	行业	项目类别	币种ID	贷款金额	实际提款额	已还本金额	利息金额
        // 按照币种ID分组
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            String splits = value.toString();

            String[] split = splits.split(",");

            String cornid = split[3];

            context.write(new Text(cornid), value);

        }
    }

    public static class MyReduce extends Reducer<Text, Text, Text, Text> {
        //返回格式：项目类别 ，币种ID，贷款额合计

        ArrayList<KV> doubles;

        @Override
        protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {

            doubles = new ArrayList<>();
        }

        @Override
        protected void cleanup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            Collections.sort(doubles, new Comparator<KV>() {
                @Override
                public int compare(KV o1, KV o2) {
                    return -(int) (o1.getScore() - o2.getScore());
                }
            });
            int i = 3;
            for (Object o : doubles.toArray()) {
                if (i <= 0) {
                    return;
                }
                KV zhuan = (KV) o;
                context.write(new Text(zhuan.getName()), new Text(String.valueOf(zhuan.getScore())));
                i--;
            }


        }

        //   //项目序号	行业	项目类别	币种ID	贷款金额	实际提款额	已还本金额	利息金额
        @Override
        protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {


            double sum = 0.0;
            String proType = "";
            for (Text value : values) {
                String[] strings = value.toString().split(",");
                double duanjier = Double.parseDouble(strings[4]);
                sum += duanjier;
                proType = "" + strings[2];

            }
            KV kv = new KV(proType +"   "+ key.toString(), sum);
            doubles.add(kv);

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Demo01.class);

        job.setJobName("demo04");


        job.setJarByClass(demo04.class);
        job.setMapperClass(demo04.MyMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, new Path("/guosai/daikuai.CSV"));
        FileOutputFormat.setOutputPath(job, new Path("/guosai/mr4"));
        job.waitForCompletion(true);


    }


}

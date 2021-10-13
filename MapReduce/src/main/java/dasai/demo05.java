package dasai;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;


public class demo05 {
    /*
    请使用MapReduce统计第1步中 汇率转化后结果Foreign_chang数据中的按照 行业分别汇总。
    各行业的贷款金额_人民币,实际提款额_人民币,已还本金额_人民币（6分）
    返回格式：
    行业，贷款金额_人民币,实际提款额_人民币,已还本金额_人民币
     */

//项目序号	行业	项目类别	币种ID	贷款金额  贷款金额人名币	实际提款额 实际提款人民币	已还本金额  已还金额人民币	利息金额  利息金额人民币 贷款余额


    public static class MyMap extends Mapper<LongWritable, Text, Text, Text> {


        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            String splits = value.toString();

            String[] split = splits.split(",");
//            行业，贷款金额_人民币,实际提款额_人民币,已还本金额_人民币
            String hangye = split[1];
            String daikuanjine = split[5];
            String shiijitikuan = split[7];
            String yihuanjiner = split[9];

            context.write(new Text(hangye), new Text(daikuanjine + "," + shiijitikuan + "," + yihuanjiner));

        }
    }

    public static class MyReduce extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            double sum_dai = 0.0;
            double sum_shi = 0.0;
            double sum_huan = 0.0;
//            行业，贷款金额_人民币,实际提款额_人民币,已还本金额_人民币
            for (Text value : values) {

                String v = value.toString();
                String[] split = v.split(",");

                sum_dai += Double.parseDouble(split[0]);
                sum_shi += Double.parseDouble(split[1]);
                sum_huan += Double.parseDouble(split[2]);
            }

            context.write(key, new Text(sum_dai + "," + sum_shi + "," + sum_huan));
        }
    }

        public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);
            job.setJarByClass(demo05.class);

            job.setJobName("demo05");


            job.setJarByClass(demo05.class);
            job.setMapperClass(demo05.MyMap.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            job.setReducerClass(demo05.MyReduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);


            FileInputFormat.setInputPaths(job, new Path("/guosai/mr2/part-r-00000"));
            FileOutputFormat.setOutputPath(job, new Path("/guosai/mr5"));
            job.waitForCompletion(true);


        }



}


package dasai;

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

/**
 * 请使用mapreduce对 贷款表Foreign_Government_Loans.csv中的贷款异常数据
 * 处理，并根据不同的规则增加不同的标识 （10分）
 * 异常数据判断规则
 * 规则1）实际提款金额 > 贷款金额 并对数据 增加一个标识 “超额提款” （5分）
 * 规则2）本金已经还完 ， 还产生利息的数据 增加一个标识 “超额付息”
 * 实际提款金额-已还本金额=0 但是 利息金额>0 的数据也是异常数据。
 */
public class demo03 {
    public static class MyMap extends Mapper<LongWritable, Text, Text, NullWritable> {

        //项目序号	行业	项目类别	币种ID	贷款金额	实际提款额	已还本金额	利息金额
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            String splits = value.toString();

            String[] split = splits.split(",");
            //贷款金额
            double daikuanjiner = Double.parseDouble(split[4]);
            double shijidaikuan = Double.parseDouble(split[5]);
            double yihuanbenjin = Double.parseDouble(split[6]);
            double lixijiner = Double.parseDouble(split[7]);

            String flag = "";
            if (shijidaikuan>daikuanjiner){
                flag=",超额提款";

            }
            if (shijidaikuan-yihuanbenjin==0&&lixijiner>0){

                flag=",超额付息";
            }
            String result = splits  + flag;

            context.write(new Text(result),NullWritable.get());

        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Demo01.class);

        job.setJobName("demo03");


        job.setJarByClass(demo03.class);
        job.setMapperClass(demo03.MyMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        FileInputFormat.setInputPaths(job, new Path("/guosai/daikuai.CSV"));
        FileOutputFormat.setOutputPath(job, new Path("/guosai/mr3"));
        job.waitForCompletion(true);


    }


}

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
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 2.	根据汇率表在贷款表中增加
 * 贷款金额_人民币,
 * 实际提款额_人民币,
 * 已还本金额_人民币,利息金额_人民币 四列，
 * 汇率转换后的数据（汇率表中币种ID与 贷款表中币种ID进行关联）（3分）
 */
public class demo02 {


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


                System.out.println(split[1]);
                System.out.println(split[2]);

                hb.put(split[0], split[1]);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            String splits = value.toString();

            String[] split = splits.split(",");

            // 币种
            String bizhong = split[3];
            // 得到汇率
            double huilu = Double.parseDouble(hb.get(bizhong));
            double daikuanjinre4 = Double.parseDouble(split[4]);

            double daikuairenmibi5 = daikuanjinre4 * huilu;
            double shijitikuan6 = Double.parseDouble(split[5]);

            double shijitikuanrenminbi7 = shijitikuan6 * huilu;
            double yihuanjine = Double.parseDouble(split[6]);

            double yihuanjinerenminbi = yihuanjine * huilu;

            double lixijine = Double.parseDouble(split[7]);
            double lixijinerenminbi = lixijine * huilu;

            //贷款余额=实际提款额-已还本金额
            double daikuanyue = shijitikuan6 - yihuanjine;


            //项目序号	行业	项目类别	币种ID	贷款金额  贷款金额人名币	实际提款额 实际提款人民币	已还本金额  已还金额人民币	利息金额  利息金额人民币 贷款金额
            String result = split[0] + ","
                    + split[1] + "," + split[2] + "," + split[3]
                    + "," + daikuanjinre4 + "," + daikuairenmibi5 + ","
                    + shijitikuan6 + "," + shijitikuanrenminbi7 + ","
                    + yihuanjine + "," + yihuanjinerenminbi + ","
                    + lixijine + "," + lixijinerenminbi + "," + daikuanyue;

            System.out.println(result);

            context.write(new Text(result), NullWritable.get());
        }


    }



    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Demo01.class);

        job.setJobName("demo2");


        job.setJarByClass(demo02.class);
        job.setMapperClass(MyMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        FileInputFormat.setInputPaths(job, new Path("/guosai/daikuai.CSV"));
        FileOutputFormat.setOutputPath(job, new Path("/guosai/mr2"));
        job.waitForCompletion(true);


    }

}

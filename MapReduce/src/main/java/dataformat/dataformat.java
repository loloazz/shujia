package dataformat;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class dataformat {
    public  static  class mymap extends Mapper<LongWritable, Text,Text, Text>{

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
//            1月20日     1月21日	大兴区	2   0	0	北京市大兴区卫健委  https://m.weibo.cn/2703012010/4462638756717942

            String[] strings = value.toString().split(",");
            String data1 = strings[0];

            data1="2020年"+data1;
//            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String data2 = "2020年"+strings[1];





            if (strings.length<8){
                return;
            }else {

                Text k = new Text(data1+","+data2+","+strings[2]+
                        ","+strings[3]+","+strings[4]+","+strings[5]+","
                        +strings[6]+","+strings[7]);


                System.out.println(k.toString());
                context.write(k, new Text(" "));

            }

        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(dataformat.class);

        job.setJobName("dataformat");

        job.setMapperClass(mymap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);



        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        System.out.println(job.waitForCompletion(true));
    }

}

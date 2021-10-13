package ReduceJoin;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;


import java.io.IOException;


/**
 * @Author: Yaolong
 * @Date: 2021/9/2 9:21
 * @Pagename: ReduceJoin
 * @ProjectName: shujia
 * @Describe:
 **/
public class ReduceJoin {
    public  static class JionMapper extends Mapper<LongWritable, Text,Text, Text>{
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            InputSplit inputSplit = context.getInputSplit();

            FileSplit fs = (FileSplit) inputSplit;
            String path = fs.getPath().toString();

            String line = value.toString();
            String[] split = line.split(",");
            String sno = split[0];

            String val = new String();
            if (path.contains("score")){
                val=line+"#";
            }else{
                val=line+"*";
            }
            Text V = new Text(val);

            Text K = new Text(sno);

            context.write(K,V);
        }
    }


public  static  class JoinReduce extends Reducer<Text,Text,Text,Text>{
    private Iterable<Text> values;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {

        int sum = 0;
        String sno = null;
        String name = null;
        String gender =null;
        for (Text value : values) {
            String sv = value.toString();
            String[] strings = sv.split(",");

            if(sv.endsWith("#")){
               sum += Integer.parseInt(strings[2].split("#")[0]);
               sno = strings[0];
            }else{
                name = strings[1];
                gender = strings[3];
            }

        }

        context.write(new Text(sno),new Text(sno+","+name+","+gender+","+sum));

    }


}

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJobName("ReduceJoin");

        job.setJarByClass(ReduceJoin.class);


        job.setMapperClass(JionMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        job.setReducerClass(JoinReduce.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));

        FileOutputFormat.setOutputPath(job,new Path(args[1]));


        if (job.waitForCompletion(true)) {
            System.out.println("seccess！");
        }else {
            System.out.println("lose");
        }


    }
}

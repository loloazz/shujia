import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class Demo08MRReadandWriteHbase {
    public  static  class Mymap extends TableMapper<Text, IntWritable>{

        private  final static  IntWritable one = new IntWritable(1);
        @Override
        protected void map(ImmutableBytesWritable key, Result value, Mapper<ImmutableBytesWritable, Result, Text, IntWritable>.Context context) throws IOException, InterruptedException {

            byte[] value1 = value.getValue("cf1".getBytes(), "clazz".getBytes());
            String clazz = Bytes.toString(value1);
            context.write( new Text(),one );
        }
    }


    public  static class  Myreducer extends TableReducer<Text,IntWritable, KeyValue>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, KeyValue, Mutation>.Context context) throws IOException, InterruptedException {



        }
    }

}

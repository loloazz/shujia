package mapJoin;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

public class mapjoin {
    public static class mapsetup extends Mapper<LongWritable, Text, Text, Text> {

        HashMap<String, String> hs = null;

        @Override
        protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            URI[] cacheFiles = context.getCacheFiles();
            URI cacheFile = cacheFiles[0];
            if (cacheFile != null && cacheFiles.length > 0) {
                FileSystem fileSystem = FileSystem.get(cacheFile, context.getConfiguration());

                FSDataInputStream fsDataInputStream = fileSystem.open(new Path(cacheFile.toString()));
                BufferedReader br = new BufferedReader(new InputStreamReader(fsDataInputStream));
                String line;
                hs = new HashMap<>();

                while ((line = br.readLine()) != null) {


                    String[] splits = line.split(",");

                    String sid = splits[0];

                    String value = line.substring(10);
                    hs.put(sid, value);
                }
                br.close();
                fsDataInputStream.close();
                fileSystem.close();

            }


        }
    }


    public static void main(String[] args) {





    }


}

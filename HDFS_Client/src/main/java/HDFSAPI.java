import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Yaolong
 * @Date: 2021/8/29 19:50
 * @Pagename: PACKAGE_NAME
 * @ProjectName: shujia
 * @Describe:
 **/
public class HDFSAPI {

    FileSystem fs = null;

    @Before
    public void init() throws IOException {
        Configuration conf = new Configuration();
        // TODO: 2021/8/29 设置HDFS的URL路径
        conf.set("fs.defaultFS", "hdfs://hadoop100:9000");
        conf.set("", "");
        fs = FileSystem.get(conf);

    }

    @Test
    // TODO: 2021/8/29 创建文件
    public void createFile() throws IOException {
        fs.create(new Path("/tatta.txt"));

    }


    @Test
    // TODO: 2021/8/29 创建多级文件夹
    public void mkdirs() throws Exception {
        boolean mkdirs = fs.mkdirs(new Path("/dir1/dir2"));
        if (mkdirs) {
            System.out.println("创建成功！！");
        }
    }

    @Test
    // TODO: 2021/8/29 删除文件  也可以删除目录！
    public void deleteFile() throws IOException {

        // 在这里  true是代表是否递归的删除！  recursive 是递归的意思
        boolean delete = fs.delete(new Path("/dir1"), true);
        if (delete) {
            System.out.println("删除成功！");

        }

    }

    @Test
    // TODO: 2021/8/29 读取文件
    public void readFile() throws IOException {
        FSDataInputStream fsD = fs.open(new Path("/myshell/easyssh"));
        BufferedReader br = new BufferedReader(new InputStreamReader(fsD));
        String line = null;
        while ((line=br.readLine())!=null){
            System.out.println(line);
        }
        br.close();
        fsD.close();

    }

    @Test
    // TODO: 2021/8/29 从本地上传文件
    public void putFile() throws IOException {

        // 注意  第一个  Path是本地文件    第二个Path是Hdfs的路径
        fs.copyFromLocalFile(new Path("F:\\shujia\\README.md"),new Path("/"));
    }

    
    @Test
    // TODO: 2021/8/29 下载文件从HDFS 
    


    @After
    // TODO: 2021/8/29 关闭资源
    public void close() throws IOException {
        fs.close();
    }

}

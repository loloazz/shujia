package file.Buff;


import java.io.*;

/**
 * @Author: Yaolong
 * @Date: 2021/7/20 15:30
 * @Pagename: file.Buff
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class fip_copy01 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("");

        FileOutputStream fileOutputStream = new FileOutputStream("");


        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);


        long start = System.currentTimeMillis();
        int i=0 ;
        byte [] datas = new byte[1024];
        while ( (i=(bufferedInputStream.read(datas))) != -1) {
            bufferedOutputStream.write(datas);

        }

        long result = System.currentTimeMillis();

        System.out.println((result - start) / 1000);
        fileInputStream.close();
        fileOutputStream.close();

    }

}

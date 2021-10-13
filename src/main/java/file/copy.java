package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class copy {
    public copy() {
    }

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\shujia\\src");
        copy(file);
        System.out.println("结束！！！");
    }

    public static void copyfile(File infile, File outfile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(infile);
        FileOutputStream fileOutputStream = new FileOutputStream(outfile);
        byte[] buf = new byte[1024];

        int len;
        while((len = fileInputStream.read(buf)) != -1) {
            fileOutputStream.write(buf, 0, len);
        }

        System.out.print("seccees!!");
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void mkdirs(File file) {

        file.mkdirs();
    }

    public static void copy(File file) throws IOException {
        File[] files = file.listFiles();
        File[] var2 = files;
        int var3 = files.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            File file1 = var2[var4];
            String name = file1.getName();
            String replace;
            if (file1.isDirectory()) {

                replace = file1.getAbsolutePath().replace("F:", "D:");

                mkdirs(new File(replace));
                System.out.println("创建目录：" + replace);

                copy(file1);
            }

            if (file1.isFile()) {

                replace = file1.getAbsolutePath().replace("F:", "D:");
                copyfile(file1, new File(replace));
                System.out.println("---------"+name);

            }

        }

    }
}

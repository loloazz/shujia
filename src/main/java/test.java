import java.io.*;

/**
 * @Author: Yaolong
 * @Date: 2021/7/22 16:08
 * @Pagename: PACKAGE_NAME
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        splitFile yao = new splitFile(18, "yaolong");
//
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("F:\\shujia\\src\\persion.txt"));
//
//
//
//        objectOutputStream.writeObject(yao);
//
//
//        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("F:\\shujia\\src\\persion.txt"));
        Object o = objectInputStream.readObject();

        splitFile s=(splitFile) o;
        s.say();



    }
}

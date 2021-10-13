package thread;

/**
 * @Author: Yaolong
 * @Date: 2021/7/23 10:53
 * @Pagename: thread
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class testThread {

    public static void main(String[] args) {


        myTread yao = new myTread("yao");

        myTread hello = new myTread("hello");

        yao.start();

        hello.start();


    }
}

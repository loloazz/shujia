package thread;

/**
 * @Author: Yaolong
 * @Date: 2021/7/23 10:51
 * @Pagename: thread
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class myTread extends Thread{

    private  String name ;

    public myTread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("线程"+getName()+"开始执行");
        int i =100;
        for (int i1 = 0; i1 < i; i1++) {
            System.out.println("线程"+getName()+"       "+"i="+i1);
        }

    }
}

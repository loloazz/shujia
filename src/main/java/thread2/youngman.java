package thread2;

/**
 * @Author: Yaolong
 * @Date: 2021/7/23 15:33
 * @Pagename: thread2
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class youngman extends Thread{
    private int speed=200;
    private int height=1000;

    @Override
    public void run() {
        int count=0;
        int sum=0;
        while (height>=0){



            height=height-speed;
            sum=sum+speed;
            System.out.println(getName()+"爬了"+sum);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }

        System.out.println(getName()+" 爬完了 "+count);
    }
}

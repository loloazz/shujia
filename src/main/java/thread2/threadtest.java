package thread2;

/**
 * @Author: Yaolong
 * @Date: 2021/7/23 15:32
 * @Pagename: PACKAGE_NAME
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class threadtest {
    public static void main(String[] args) {
        oldman oldman = new oldman();
        oldman.setName("老头");
        youngman youngman = new youngman();
        youngman.setName("年轻人");


        oldman.start();

        youngman.start();
    }
}

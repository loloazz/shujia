package UDP;

import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Yaolong
 * @Date: 2021/7/26 14:42
 * @Pagename: UDP
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class sendCli {

    public static void main(String[] args) throws IOException {


        int prot = 8888;

        String strip ="10.7.156.73";
        byte[] ip = strip.getBytes(StandardCharsets.UTF_8);



        // TODO: 2021/7/26 ip地址和端口号进行绑定
        DatagramSocket datagramSocket = new DatagramSocket(prot);


        // TODO: 2021/7/26  创建数据包

        byte[] bytes = "你好啊！！".getBytes(StandardCharsets.UTF_8);


        DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length,InetAddress.getByName("10.7.156.73"),9999);

        // TODO: 2021/7/26 调用发送函数进行发送数据包
        datagramSocket.send(datagramPacket);


        System.out.println("发送成功"+"*******************"+new String(bytes));
        // TODO: 2021/7/26 关闭资源

        datagramSocket.close();

    }
}

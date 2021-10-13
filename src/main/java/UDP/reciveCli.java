package UDP;

import java.io.IOException;
import java.net.*;

/**
 * @Author: Yaolong
 * @Date: 2021/7/26 14:42
 * @Pagename: UDP
 * @ProjectName: shujia_java
 * @Describe:接收端
 **/
public class reciveCli {
    public static void main(String[] args) throws IOException {

        int prot = 9999;

        // TODO: 2021/7/26 通过InetAddress.getByName 得到对应的网络地址对象
        InetAddress address = InetAddress.getByName("10.7.156.73");

        // TODO: 2021/7/26 ip地址和端口号进行绑定
        DatagramSocket datagramSocket = new DatagramSocket(prot);

        byte[] bytes = new byte[1024];

        DatagramPacket datagramPacket = new DatagramPacket(bytes,0,bytes.length);


        datagramSocket.receive(datagramPacket);

        System.out.println(new  String(datagramPacket.getData(),datagramPacket.getOffset(),datagramPacket.getLength()));


    }


}

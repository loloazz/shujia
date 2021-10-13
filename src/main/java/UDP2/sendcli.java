package UDP2;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Author: Yaolong
 * @Date: 2021/7/26 15:54
 * @Pagename: UDP2
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class sendcli {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        // TODO: 2021/7/26  创建udp对象 指定端口号
        DatagramSocket datagramsocket = new DatagramSocket(9999);
        // TODO: 2021/7/26 创建数据包
//        Scanner scanner = new Scanner(System.in);
        while (true) {


//            String next = scanner.next();
            String next = "ooo";

            byte[] bytes = next.getBytes(StandardCharsets.UTF_8);

            DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getByName("10.7.156.134"), 1000);

            try {
                datagramsocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

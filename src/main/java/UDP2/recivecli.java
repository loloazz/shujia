package UDP2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Yaolong
 * @Date: 2021/7/26 15:54
 * @Pagename: UDP2
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class recivecli {
    public static void main(String[] args) throws IOException {
        // TODO: 2021/7/26  创建udp对象 指定端口号
        DatagramSocket datagramsocket = new DatagramSocket(8888);
        //接受
        byte[] bytes = new byte[1024];
        DatagramPacket data = new DatagramPacket(bytes, 0, bytes.length);

        while (true) {

            datagramsocket.receive(data);
            byte[] recedata = data.getData();

            System.out.println(new String(recedata, data.getOffset(), data.getLength()));
            String s = new String(recedata, data.getOffset(), data.getLength());

            if ("byebye".equals(s)) {
                break;

            }

        }


        System.out.println("拜拜！！");
        datagramsocket.close();


    }


}

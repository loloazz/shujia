package TCP1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 9:49
 * @Pagename: TCP1
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class client {
    public static void main(String[] args) throws IOException {


        Socket socket = new Socket("127.0.0.1", 8888);

        Scanner scanner = new Scanner(System.in);
        System.out.println("连接成功！！！"+socket.getPort());

        OutputStream outputStream = null;
        InputStream inputStream = null;

        while (true) {

            byte[] bytes = scanner.next().getBytes(StandardCharsets.UTF_8);


           outputStream = socket.getOutputStream();


            outputStream.write(bytes);


            inputStream = socket.getInputStream();


            byte[] rebytes = new byte[1024];
            int read = inputStream.read(rebytes);
            System.out.println(new String(bytes,0,read));

            if (read==-1) {
                break;
            }



        }

        outputStream.close();
        inputStream.close();
    }
}
package TCP1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 9:49
 * @Pagename: TCP1
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket( 8888);
        System.out.println("等待连接。。。。。。。");


        Socket accept = socket.accept();
        System.out.println("连接成功！！！"+accept.getLocalAddress());
        InputStream inputStream = null;
        OutputStream outputStream= null;

        while (true) {



             inputStream = accept.getInputStream();

            byte[] bytes = new byte[1024];

            int read = inputStream.read(bytes);

            String s = new String(bytes, 0, read);
            System.out.println("收到信息！！！！            "+s);

            if (read == -1) {
                break;
            }


            String str =s;

            byte[] sendbytes = str.getBytes(StandardCharsets.UTF_8);


             outputStream = accept.getOutputStream();


            outputStream.write(sendbytes);




        }
        inputStream.close();
        outputStream.close();
    }
}

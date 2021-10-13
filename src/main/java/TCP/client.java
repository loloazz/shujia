package TCP;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 9:38
 * @Pagename: TCP
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);

        byte[] bytes = "hello".getBytes(StandardCharsets.UTF_8);



        OutputStream outputStream = socket.getOutputStream();


        outputStream.write(bytes);


        outputStream.close();

    }
}

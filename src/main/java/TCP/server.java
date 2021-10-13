package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.BatchUpdateException;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 9:38
 * @Pagename: TCP
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class server {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket( 9999);


        Socket accept = socket.accept();
        InputStream inputStream = accept.getInputStream();

        byte[] bytes = new byte[1024];

        int read = inputStream.read(bytes);

        String s = new String(bytes, 0, read);
        System.out.println(s);


    }

}

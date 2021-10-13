package TCPTest;

import javax.lang.model.element.VariableElement;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 15:53
 * @Pagename: TCPTest
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);


        System.out.println("等待连接");
        Socket accept = serverSocket.accept();

        InputStream inputStream = accept.getInputStream();
        System.out.println("连接成功！！"+ accept.getInetAddress());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String s = bufferedReader.readLine();


        if (s.equals("yaolong123456")) {

            OutputStream outputStream = accept.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("登陆成功！！！！");
            bufferedWriter.flush();
        }else {
            OutputStream outputStream = accept.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("登陆失败检查密码用户名！！！！");
            bufferedWriter.flush();
        }




    }
}

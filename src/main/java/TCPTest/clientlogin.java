package TCPTest;

import javax.lang.model.element.VariableElement;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Yaolong
 * @Date: 2021/7/27 15:53
 * @Pagename: TCPTest
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class clientlogin {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 9999);


        System.out.println("请输入用户名");

        Scanner scanner = new Scanner(System.in);

        String user = scanner.next();

        System.out.println("请输入密码");

        String passwd=scanner.next();





        String str = user+passwd;

        OutputStream outputStream = socket.getOutputStream();


        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        bufferedWriter.write(str);

        bufferedWriter.flush();




//        InputStream inputStream = socket.getInputStream();
//
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        String s = bufferedReader.readLine();
//
//
//        System.out.println(s);



    }
}

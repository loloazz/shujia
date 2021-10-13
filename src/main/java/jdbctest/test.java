package jdbctest;


import java.sql.*;

/**
 * @Author: Yaolong
 * @Date: 2021/8/13 10:03
 * @Pagename: jdbctest
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO: 2021/8/13  加载驱动类
         Class.forName("com.mysql.cj.jdbc.Driver");
        // TODO: 2021/8/13 创建链接对象

        Connection root = DriverManager.getConnection("jdbc:mysql://hadoop100:3306/mysqltest", "root", "123456");

        String sql = "select * from emp where sal>? ";
        PreparedStatement preparedStatement = root.prepareStatement(sql);

        preparedStatement.setString(1,"800");


        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){

            int empno = resultSet.getInt("empno");
            System.out.println(empno);

        }

        resultSet.close();
        preparedStatement.close();
        root.close();



    }
}

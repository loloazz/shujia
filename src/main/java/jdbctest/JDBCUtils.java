package jdbctest;


import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author: Yaolong
 * @Date: 2021/8/13 15:18
 * @Pagename: jdbctest
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class JDBCUtils {


    private static String URL;
    private static String DRIVER;
    private static String USER;
    private static String PASSWORD;
    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet resultSet;

    static {


        try {
            Properties p = new Properties();
            p.load(new FileInputStream("src/main/resources/mysql.properties"));

            DRIVER = p.getProperty("driver");
            URL = p.getProperty("url");
            USER = p.getProperty("user");
            PASSWORD = p.getProperty("password");
            Class.forName(DRIVER);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Connection getconn() throws SQLException {

        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    public ResultSet select(String sql) throws SQLException {
        ps = conn.prepareStatement(sql);
        resultSet = ps.executeQuery();
        return resultSet;
    }

    public void close() throws SQLException {
        ps.close();
        conn.close();
    }




}

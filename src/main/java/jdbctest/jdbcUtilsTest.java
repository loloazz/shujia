package jdbctest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Yaolong
 * @Date: 2021/8/13 16:03
 * @Pagename: jdbctest
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class jdbcUtilsTest {
    public static void main(String[] args) throws SQLException {
        JDBCUtils jdbcUtils = new JDBCUtils();
        jdbcUtils.getconn();
        String sql = "select * from emp where sal=800";

        ResultSet select = jdbcUtils.select(sql);
        while (select.next()){
            int empno = select.getInt("empno");

            System.out.println(empno);
        }

        jdbcUtils.close();

    }
}

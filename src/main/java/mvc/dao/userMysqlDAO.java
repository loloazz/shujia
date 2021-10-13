package mvc.dao;


import java.sql.*;

/**
 * @Author: Yaolong
 * @Date: 2021/8/16 20:22
 * @Pagename: mvc.dao
 * @ProjectName: shujia_java
 * @Describe: 数据访问层。  只对数据库进行原子的操作！
 **/
public class userMysqlDAO {

    private static Connection conn;

    // 对数据库的初始化，静态代码
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://hadoop100:3306/shujia", "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 2021/8/16 检查是否，存在！！！
    public boolean isexist(int sid) throws SQLException {
        String sql = "select * from user where sid = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, sid);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return true;
        }


        return false;
    }

    // TODO: 2021/8/16 根据sid删除操作！
    public boolean delete(int sid) throws SQLException {
        String sql = "delete from user where sid = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, sid);
         ps.executeUpdate();

        return true;
    }

    // TODO: 2021/8/16  数据库的插入操作
    public boolean insert(int sid, String sname, int age) {
        String sql = "insert into user (sid,sname,age) values(?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2,sname);
            ps.setInt(3,age);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

//    public void commit(){
//        String sql = "commit";
//        PreparedStatement ps = null;
//        try {
//            ps = conn.prepareStatement(sql);
//            ResultSet resultSet = ps.executeQuery();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }


}

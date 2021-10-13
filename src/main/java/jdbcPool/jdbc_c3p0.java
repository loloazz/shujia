package jdbcPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.jboss.C3P0PooledDataSource;
import org.junit.Test;

import javax.naming.NamingException;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Yaolong
 * @Date: 2021/8/17 19:53
 * @Pagename: jdbcPool
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class jdbc_c3p0 {
    @Test
    public  void  c3p0test() throws PropertyVetoException, NamingException, SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setMaxPoolSize(100);
        dataSource.setMinPoolSize(5);
        dataSource.setUser("root");
        dataSource.setJdbcUrl("jdbc:mysql://hadoop100:3306/shujia?useUnicode=true&CharacterEncoding=utf-8 ");
        dataSource.setPassword("123456");
        String sql="select * from user ";

        dataSource.setInitialPoolSize(5);
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getInt("sid"));
        }


    }
}

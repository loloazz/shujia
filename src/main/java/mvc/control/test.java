package mvc.control;

import mvc.bean.User;
import mvc.service.userService;

import javax.lang.model.element.VariableElement;
import java.sql.SQLException;

/**
 * @Author: Yaolong
 * @Date: 2021/8/16 21:27
 * @Pagename: mvc.control
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class test {
    public static void main(String[] args) {
        User zhansan = new User(1000, "zhansan", 50);
        User lisi = new User(1001, "Lisi", 60);

        userService userService = new userService();
        try {
            String s = userService.insertSerive(zhansan);
            System.out.println(s);
            String s1 = userService.insertSerive(lisi);
            System.out.println(s1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            String s = userService.deleteService(zhansan);
//            String s1 = userService.deleteService(lisi);
//            System.out.println(s+"\n"+s1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }


}

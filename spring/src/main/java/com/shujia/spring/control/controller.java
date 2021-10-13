package com.shujia.spring.control;

import com.shujia.spring.bean.User;
import com.shujia.spring.dao.userMysqlDAO;
import com.shujia.spring.service.userService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @Author: Yaolong
 * @Date: 2021/8/17 11:03
 * @Pagename: com.shujia.spring
 * @ProjectName: shujia_java
 * @Describe:
 **/

@RestController
public class controller {
    public controller() {
        System.out.println("这是默认构造器");
    }
    @GetMapping("/data")
    public String data(){

        System.out.println("正在登录");
        return  "执行登录";

    }
    @GetMapping("/insert")
    public String insert(int sid ,String sname,int age) throws SQLException {
        System.out.println("正在登录！！");
        User user = new User(sid, sname, age);
        userService userService = new userService();
        String s = userService.insertSerive(user);
        return "ok";
    }




}

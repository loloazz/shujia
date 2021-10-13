package com.shujia.spring.service;



import com.shujia.spring.bean.User;
import com.shujia.spring.dao.userMysqlDAO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @Author: Yaolong
 * @Date: 2021/8/16 20:45
 * @Pagename: mvc.service
 * @ProjectName: shujia_java
 * @Describe: 业务逻辑层，对数据访问层进行拼装
 **/


public class userService {

    private userMysqlDAO umd=null;
        public  String deleteService(User user) throws SQLException {

            int sid = user.getSid();
             umd = new userMysqlDAO();
            if (umd.isexist(sid)){
                boolean delete = umd.delete(sid);
                System.out.println("delete结果为"+delete);
//                umd.commit();
                return "删除成功！！";
            }else{
                return sid+"不存在删除失败";
            }
        }

        public String insertSerive(User user) throws SQLException {

            int sid = user.getSid();
            int age = user.getAge();
            String sname = user.getSname();
            umd = new userMysqlDAO();
        if (umd.isexist(sid)){
            return  "插入失败！原因：sid已经存在！";
        }else{
            boolean insert = umd.insert(sid, sname, age);
            if (insert){
//                umd.commit();
                return "插入成功"+"----------"+sid+" "+sname+" "+age;
            }else {
                return "未知原因，插入失败";
            }
        }
        }




}

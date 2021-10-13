package mvc.bean;


/**
 * @Author: Yaolong
 * @Date: 2021/8/16 20:21
 * @Pagename: mvc.bean
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class User {

    private  int sid;
    private  String sname;
    private int age;

    public User(int sid, String sname, int age) {
        this.sid = sid;
        this.sname = sname;
        this.age = age;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

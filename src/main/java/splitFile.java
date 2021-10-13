import java.io.Serializable;

/**
 * @Author: Yaolong
 * @Date: 2021/7/21 16:56
 * @Pagename: PACKAGE_NAME
 * @ProjectName: shujia_java
 * @Describe:
 **/
public class splitFile implements Serializable {
   private int age;
   private String name;
//   private static final long

   public splitFile(int age, String name) {
      this.age = age;
      this.name = name;
   }

   public splitFile() {
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void say(){
      System.out.println("hello"+getName());
   }



}

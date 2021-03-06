# java的三层架构
![输入图片说明](https://images.gitee.com/uploads/images/2021/0816/221336_388d95a8_8294342.png "屏幕截图.png")

## 什么是三层架构：

### 数据访问层（DAL）（dao）

​			作用：访问数据库。原子性的进行增删改查。

​			解释：

​					原子性：以删除为例： 我不管你存在不存在，原子层不管能不能删，我还删。就是这一层，只有一个功能，那就是删除。

### 业务逻辑层：（service）

​		作用：处理业务逻辑。逻辑性的增删改查。（DAL的组装）

​		解释：这一层，还是以删除为例，我要删除一个东西，我需要进行一系列的判断。例如：我又没有权限删，要删除的数据存不存在。在这时要做出判断。说白一点：业务逻辑层，就是对数据访问层的一种完善。

### 表示层（USL）：

​		作用：用户交互。表示层前台（JSP）+ 表示层后台（Servlet）

​			

![输入图片说明](https://images.gitee.com/uploads/images/2021/0816/221357_0d8c22bb_8294342.png "屏幕截图.png")

### 3+X：

​	三层架构 + X  辅助类（实体类加工具类）

​	辅助类：

​			工具类：utilsClass 主要是对访问数据的库方法，进行封装，方便数据访问层的使用。

​			实体类：主要就对多个属性的封装成一个类，通过传输对象的方法，而不是通过传输一个一个属性字段，方式在三层结构中进行，这样减少了系统 I/O的操作。（bean）

​	

注意：写的顺序，是从下向上写。
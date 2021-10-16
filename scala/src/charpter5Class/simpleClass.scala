package charpter5Class

class simpleClass {
  private var value = 0;//必须要对变量初始化
  def increment() {//方法默认是共有的
    value+=1
  }

  def current()=value


}
object simpleClass{
  def main(args: Array[String]): Unit = {
    val clazz = new simpleClass()
    clazz.increment()
    clazz.increment()
    println(clazz.value)
    println(clazz.current())

  }
}
import scala.io.StdIn.readLine

object test01 {
  def main(args: Array[String]): Unit = {
    println("请输入名字：")
    var name = readLine()
    println(name)

    println("请输入年龄：")
    var age = readLine()
    println(age)
    println("请输入身高：")
    var high = readLine()
    println(high)
    println(s"姓名：$name 年龄为：$age 身高为：$high")
  }

}

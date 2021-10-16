package detail

object varDemo01 {
  def main(args: Array[String]): Unit = {

    // 类型推导。
    var num  = 10 //这时num就是Int

    println(num.isInstanceOf[Int])


  }

}

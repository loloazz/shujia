package homework

object homewoork02 {
  def main(args: Array[String]): Unit = {
    println(judgment_signum(-100))


    // {} 的值是unit 输出的是空（）
    var i = {}
    println(i)
    countdown(50)


  }

  //判断一个数的符号位
  def judgment_signum(i: Int) = {

    if (i > 0)
      1
    else if (i < 0)
      -1
    else
      0
  }

  def countdown(n:Int):Unit={
    for(em <- (0 to n).reverse )
      println(em)
  }


}

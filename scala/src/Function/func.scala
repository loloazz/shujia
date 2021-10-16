package Function

import java.text.MessageFormat

object func {

  def main(args: Array[String]): Unit = {
    println(fac(4))
    println(decorate("yaolong"))
    println(decorate("bubu", "er"))

    println(sum(1, 23, 45, 6, 7, 8, 55, 3))
    println(sum(1 to 5 by 2: _*))

    println(recursiveSum(12, 3445, 123, 45, 123))

    val str  =MessageFormat.format("the answer to {0} is {1}","everything",42.toString)
    println(str)
  }

  def fac(n: Int): Int = if (n <= 0) 1 else n * fac(n - 1)


  def decorate(Str: String, left: String = "[", right: String = "]") = {
    left + Str + right
  }

  //变长参数
  // * 是通配符代表任意多个int型的数据

  def sum(args: Int*) = {
    var result = 0
    for (arg <- args) {
      result += arg
    }
    result

  }


  def recursiveSum(args:Int*):Int={
    if(args.length==0) 0
    else args.head +recursiveSum(args.tail:_*)
  }
}
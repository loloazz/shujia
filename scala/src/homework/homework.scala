package homework

import scala.io.StdIn
import scala.util.control.Breaks.{break, breakable}

object homework {
  def main(args: Array[String]): Unit = {
    // 判断一个数是不是质数
    println("请输入一个数")
    val input = StdIn.readInt()
    var flag = true

    breakable {
      for (i <- (2 until input)) {
        if (input % i == 0) {
          flag = false
          break()
        }

      }
    }
    println(flag)


  }
}

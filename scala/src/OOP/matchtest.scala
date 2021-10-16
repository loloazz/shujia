package OOP

object matchtest {

  def main(args: Array[String]): Unit = {

    println(abs("na"))

    println(describe(List(1, 2, 3, 4, 5)))

    //数组例外，可保留泛型
    println(describe(Array(1, 2, 3, 4, 5, 6)))
    println(describe(Array("abc")))

    //匹配数组
    for (arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(1, 1, 0, 1), Array("hello", 90))) { // 对一个数组集合进行遍历

      val result = arr match {
        case Array(0) => "0" //匹配Array(0) 这个数组

        case Array(x, y) => x + "," + y //匹配有两个元素的数组，然后将将元素值赋给对应的x,y

        case Array(x, y, z) => x + "," + y + "," + z

        case Array(0, _*) => "以0开头的数组" //匹配以0开头和数组

        case Array(1, _*) => "以1开头的数组  " //匹配以1开头的数组
        case _ => "something else"
      }

      println("result = " + result)
    }

    //对一个元组集合进行遍历
    for (tuple <- Array((0, 1), (1, 0), (1, 1), (1, 0, 2))) {

      val result = tuple match {
        case (0, _) => "0 ..." //是第一个元素是0的元组
        case (y, 0) => "" + y + "," + "0" // 匹配后一个元素是0的对偶元组
        case (a, b) => "" + a + "," + b
        case _ => "something else" //默认

      }
      println(result)
    }


    val map = Map("A" -> 1, "B" -> 0, "C" -> 3)
    for ((k, v) <- map) { //直接将map中的k-v遍历出来
      println(k + " -> " + v) //3个
    }
    println("----------------------")

    //遍历value=0的 k-v ,如果v不是0,过滤
    for ((k, 0) <- map) {
      println(k + " --> " + 0) // B->0
    }

    println("----------------------")
    //if v == 0 是一个过滤的条件
    for ((k, v) <- map if v >= 1) {
      println(k + " ---> " + v) // A->1 和 c->33
    }
  }


  def abs(x: Any) = x match {
    case i: Int if i >= 0 => i
    case j: Int if j < 0 => -j
    case _ => "shabi"
  }

  def describe(x: Any) = x match {

    case i: Int => "Int"
    case s: String => "String hello"
    case m: List[_] => "List"
    case c: Array[Int] => "Array[Int]"
    case someThing => "something else " + someThing
  }
}

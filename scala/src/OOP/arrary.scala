package OOP

object arrary {
  def main(args: Array[String]): Unit = {

    val array = new Array[Double](10)
    println(array.length)
    println(array.mkString(","))

    var a = 10
    var array1 = 0.1 +: array :+ 20 :+ a
    array1.update(0,20.0)
    array1.foreach(println)
  }
}

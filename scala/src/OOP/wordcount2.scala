package OOP

object wordcount2 {
  def main(args: Array[String]): Unit = {
    val tupleList = List(("Hello Scala Spark World ", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))
    val value = tupleList.map(t => (t._1)).flatMap(_.split(" ")).groupBy(word=>word)
    val value1 = value.map(kv => (kv._1, kv._2.size))
    println(value1)

  }

}

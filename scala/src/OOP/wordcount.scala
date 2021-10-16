package OOP

import scala.collection.mutable

object wordcount {
  def main(args: Array[String]): Unit = {
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    val value = stringList.flatMap(str => str.split(" "))
//    println(value)

    var value1 = value.groupBy(word => word)

    println(value1)
    val value2 = value1.map(tup => (tup._1, tup._2.size))
    println(value2)
    val list = value2.toList
      println(list)
    val value3 = list.sortWith((x, y) => {x._2 > y._2})
    value3.foreach(println)

  }




}

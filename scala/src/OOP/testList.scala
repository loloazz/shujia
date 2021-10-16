package OOP

import scala.collection.mutable

object TestList {

  def main(args: Array[String]): Unit = {

    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val nestedList: List[List[Int]] = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    val wordList: List[String] = List("hello world", "hello atguigu", "hello scala")

    //（1）过滤
    println(list.filter(x => x % 2 == 0))

    //（2）转化/映射
    println(list.map(x => x + 1))

    //（3）扁平化
    //val nestedList: List[List[Int]] = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(nestedList.flatten)

    //（4）扁平化+映射 注：flatMap相当于先进行map操作，在进行flatten操作
    println(wordList.flatMap(x => x.split(" ")))

    //（5）分组 x => x % 3  HashMap(0 -> List(3, 6, 9), 1 -> List(1, 4, 7), 2 -> List(2, 5, 8))
    println(list.groupBy(x => x % 3))

    val i = list.reduce((x, y) => x - y)
    println(i)
    // 10 是一个种子值 代表以10开始
    val functionToTuple = list.foldLeft(10) ((x, y) => x - y)
    println(functionToTuple)


    // 两个Map的数据合并
    val map1 = mutable.Map("b"->2, "c"->3,"a"->1)
    val map2 = mutable.Map("a"->4, "b"->5, "d"->6)
    // 将两个map 聚合成一个
    val map3: mutable.Map[String, Int] = map1.foldLeft(map2) //因为map2是种子值  所以先从 map2 开始
            //def foldLeft[B](z: B)(op: (B, A) => B): B
    {
          // 注意这里的map对应的是map2   kv 对应的map1
      (map,kv)=>
        {
          val k = kv._1
          println(k)
          val v = kv._2
          println(v)
          val i = map.getOrElse(k, 0) + v
          map.put(k,i)
          map
        }
    }

    println(map3)
  }



}

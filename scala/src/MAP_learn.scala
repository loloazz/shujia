import scala.collection.mutable
import scala.collection.immutable._


object MAP_learn {
  def main(args: Array[String]): Unit = {
    val scores = Map("yaoong" -> 10, "da" -> 20)
    println(scores)
    val hsm = new mutable.HashMap[String, Int]

    //    val i = scores("yaolong")//找不到抛异常
    val i1 = scores("yaoong")
    println(i1)

    val maybeInt = scores.get("yaoong") //java的方式
    //    println(i)
    //    println(maybeInt)


    //为了不让它找不到抛出异常则  用contains查看一下
    val x = if (scores.contains("yaolong"))
      scores("yaolong")
    else
      0

    println(x)

    // h或者用   getOrElse   第一个是”、key   第二个是找不到时的默认值
    val value = scores.getOrElse("yaolong", "没有找到")
    println(value)
    println("****************" * 3)
    // TODO: 更新映射的值  Map默认是不可变的
    val score = collection.mutable.Map[String, Int]()
    // 添加
    score("Bob") = 20

    // 修改
    score("Bob") = 10

    //批量添加  直接用   +=

    score += ("lal" -> 30, "hello" -> 50)
    println(score)

    //删除某个键和值 -=
    score -= "lal"
    println(score)

    // TODO:  迭代映射
    for ((k, v) <- score) println(k, "," + v)
    // k , v 反转

    val newmap = for ((k, v) <- score) yield (v, k)
    println(newmap)

    // 只取 key 或者value

    // 只取key
    val set = score.keySet
    for (s <- set) println(s)
    //只取value
    val values = score.values
    for (v <-values) println(v)




  }



}

package OOP

object maptest {
  def main(args: Array[String]): Unit = {

    // Map
    //（1）创建不可变集合Map
    val map = Map( ("a","1"),"b"->2, "c"->3 ,"d"-> 4)

    //（3）访问数据
    for (e<-map.keys){

      println(map(e))
    }

    //（4）如果key不存在，返回0
    val value = map.getOrElse("d",20)
    println(value)
    //（2）循环打印
    map.foreach((kv)=>{println(kv)})
  }


}

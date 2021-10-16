object tuple {
  def main(args: Array[String]): Unit = {
    val tuple1 = Tuple3(1, 2.1, "ffff")
 
    
    println(tuple1)

    val value = tuple1._1
    val value1 = tuple1._2
    val value2 = tuple1._3
    println(value,value1,value2)

    val (i,j,z) = tuple1
    println(i)
    println(j)
    println(z)

    val(k,l,_)=tuple1
    println(k)
    println(l)

    val tuple2 = "New York Is Ve".partition(_.isUpper)

    println(tuple2)
  }
}

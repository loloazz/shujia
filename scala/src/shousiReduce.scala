object shousiReduce {
  def main(args: Array[String]): Unit = {
    val array = Array(10, 20, 30, 322, 63)
    println(Reduce(array, _ + _))




  }


  def  Reduce(array: Array[Int],op:(Int,Int)=>Int): Int ={
    var lastRuce =array(0)
    for (i <- 1 until array.length){

      lastRuce = op(lastRuce,array(i))

    }

    lastRuce
  }




}

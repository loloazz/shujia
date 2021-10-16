object shousiforeach {
  def main(args: Array[String]): Unit = {
    val array = Array(10, 20, 30, 322, 63)
    foreach(array, (a) =>  {

      println(a+1)
    })
  }

  def foreach(arr: Array[Int], op: Int => Unit) = {
    for (elem <- arr) {
      op(elem)

    }


  }


}

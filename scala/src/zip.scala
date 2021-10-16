object zip {
  def main(args: Array[String]): Unit = {
    val symbols = Array("<", "-", ">","ee")
    val ints = Array(2, 10, 2,20)
    val pairs = symbols.zip(ints).toMap

    val tuples = "hello".zip("world")
    println(tuples)
    println(pairs)
  }
}

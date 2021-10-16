object test02 {
  def main(args: Array[String]): Unit = {

    val nums = Seq(1,2,3)
    for (n<- nums) {
      println(n)
    }

    var rateings  = Map(
      "lady in the Wather"->2.0,
      "Snake on a Plane"->3.0,
      "hello" -> 4.0
    )
    for((name,rate)  <-rateings) println(s"movies: $name ,Rateing:$rate")
    println("*********************")






    rateings.filter{
      case (str, d) => d>2.0
    }.foreach{
       case (str, d) => println(s"key:$str,rate:$d")
    }
    }
  }



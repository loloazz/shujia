import scala.math.{pow, sqrt}

object test03 {
  def main(args: Array[String]): Unit = {
    var nums= List(1,2,351,58)
    nums=for(n<-nums)yield n*2

//    println(nums.length)

    var names=List("dada","dadeeaea","idaieaefcc")
    var name=("uiiiidd")
      name match {
        case n if(n.length>4) => println(n)
      }

//    println(for(n<-names) yield n.drop(1).capitalize)

    var num= 3
    var re=sqrt(num)
    println(re)
    println(re*re)
////    var r: BigInt=pow(2,1024)
//    println(r)
    var o = BigInt(2)
    o.pow(1024)
    println(o)


  }


}

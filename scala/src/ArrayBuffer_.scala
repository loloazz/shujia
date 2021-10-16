import scala.collection.mutable.ArrayBuffer

object ArrayBuffer_ {
  def main(args: Array[String]): Unit = {
    var i =ArrayBuffer[String]()
    i.append("lala")
    var ints = List.range(1,100000)
    var douleints = ints.map(_*3)
  }

}

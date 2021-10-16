import jdk.nashorn.internal.objects.NativeDebug.map

import java.util.concurrent.locks.Condition

object shousifilter {

  def main(args: Array[String]): Unit = {
    var arr = Array(10,20,30,66,45,9,6,31)
    var arr2 =filter(arr,x=> x%2!=0)

    foreach(arr2,println)
    val arr3 = map(arr,x=>x * x)

   foreach(arr3,println)

  }
  def  filter(array: Array[Int],condition:Int =>Boolean) ={
    for (elem <- array if condition (elem))  yield elem
  }


  def foreach(arr: Array[Int], op: Int => Unit) = {
    for (elem <- arr) {
      op(elem)
    }
  }
  def map(array: Array[Int],op:Int=>Int)={
    for (elem <- array) yield op(elem)

  }
}

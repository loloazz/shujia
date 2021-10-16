package OOP

object TestFunction {
  def main(args: Array[String]): Unit = {

    println(calculator(1,2,plus))
    println(calculator(2, 6, multiply))




  }

  //高阶函数————函数作为参数
  def calculator(a:Int,b:Int,operater:(Int,Int)=>Int)={

  operater(a,b)
}
  def plus(a:Int,b:Int)={
    a+b
  }

  def multiply(a:Int,b:Int)={
    a*b
  }



}

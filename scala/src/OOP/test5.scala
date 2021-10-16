package OOP

abstract class Persons {
    val name :String
  def hello() ={
    println("你好人类")
  }}

class teacher extends Persons {
    override val name: String ="ddd"

    override def hello(): Unit = {
      println("你好老师")
    }
  }

object test5{
  def main(args: Array[String]): Unit = {

    val teacher  = new teacher
    teacher.hello()
  }
}
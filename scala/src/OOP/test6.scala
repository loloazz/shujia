package OOP

object test6 {
  def main(args: Array[String]): Unit = {
    val student = new Student
    println(student.isInstanceOf[Student])
    println(student.asInstanceOf[Student])
    val value = classOf[Student]
    println(value)

//    println(student.name)
//    println(Student.ispeople)

  }
}


class Student{
  var name:String = "学生"

}
object Student{

  var ispeople:Boolean = true
}

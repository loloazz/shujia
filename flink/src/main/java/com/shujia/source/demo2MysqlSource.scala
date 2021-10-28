package com.shujia.source

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

object demo2MysqlSource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val studentsDS: DataStream[students] = env.addSource(new MysqlSource)

    studentsDS.print()



    env.execute()


  }
}

case class students(id: String, name: String, age: Int, gender: String, clazz: String)


class MysqlSource extends RichSourceFunction[students] {

  var con: Connection = _

  override def open(parameters: Configuration): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    con = DriverManager
      .getConnection("jdbc:mysql://hadoop100:3306/students", "root", "123456")

  }


  override def run(ctx: SourceFunction.SourceContext[students]): Unit = {
    val preparedStatement: PreparedStatement = con.prepareStatement("select * from student  ")
    val resultSet: ResultSet = preparedStatement.executeQuery()

    while (resultSet.next()) {
      val id: String = resultSet.getString("id")
      val name: String = resultSet.getString("name")
      val age: Int = resultSet.getInt("age")
      val gender: String = resultSet.getString("gender")
      val clazz: String = resultSet.getString("clazz")

      val student = students(id, name, age, gender, clazz)


      //将数据发送到下游
      ctx.collect(student)

    }

  }


  override def cancel(): Unit = {
  }

  override def close(): Unit = {
    con.close()
  }

}

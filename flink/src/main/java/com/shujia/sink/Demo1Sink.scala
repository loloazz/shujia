package com.shujia.sink


import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import java.sql.{Connection, DriverManager, PreparedStatement}

object Demo1Sink {

  // 将数据 插入到mysql
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val studentDS: DataStream[String] = env.readTextFile("data/students.txt")


    studentDS.print()
    studentDS.addSink(new Mysink)


    env.execute()
  }
}

/**
 * 自定义sink SinkFunction： 并行的 RichSinkFunction: 多了open和close方法
 */
class Mysink extends RichSinkFunction[String] {
  var con: Connection = _

  override def open(parameters: Configuration): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    con = DriverManager
      .getConnection("jdbc:mysql://hadoop100:3306/students", "root", "123456")

  }


  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
    val preparedStatement: PreparedStatement = con.prepareStatement("insert  into student values (?,?,?,?,?)")

    val lines: Array[String] = value.split(",")

    val id: String = lines(0)
    val name: String = lines(1)
    val age: Int = lines(2).toInt
    val gender: String = lines(3)
    val clazz: String = lines(4)

    preparedStatement.setString(1, id)
    preparedStatement.setString(2, name)
    preparedStatement.setInt(3, age)
    preparedStatement.setString(4, gender)
    preparedStatement.setString(5, clazz)

    preparedStatement.executeUpdate()


  }


  override def close(): Unit = {
    con.close()
  }


}
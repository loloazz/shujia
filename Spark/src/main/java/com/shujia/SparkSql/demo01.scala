package com.shujia.SparkSql

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, DataFrameWriter, Row, SparkSession}


object demo01 {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .master("local")
      .appName("sql")
      .config("spark.sql.shuffle.partitions", 1)
      .getOrCreate()

    val df: DataFrame = spark.read.json("data/students.json")

    val sc: SparkContext = spark.sparkContext


    df.show()
    df.printSchema()

    import spark.implicits._
    //列对象表达式
    df.where($"age" >= 23).show()
   //  字符串表达式
    df.where("gender = '男'").where(($"age" >= 23)).show()
//      +---+--------+------+----------+------+
//      |age|   clazz|gender|        id|  name|
//      +---+--------+------+----------+------+
//      | 24|文科六班|    男|1500100002|吕金鹏|
//      | 24|理科三班|    男|1500100004|葛德曜|
//      | 23|理科六班|    男|1500100010|羿彦昌|
//      | 24|文科二班|    男|1500100013|逯君昊|


    // 聚合函数
    val clazzPersonNum: DataFrame = df.groupBy($"clazz").count()

    //+--------+-----+
    //|   clazz|count|
    //+--------+-----+
    //|文科六班|  104|
    //|理科六班|   92|
    //|理科三班|   68|



//  注册临时视图      一般用在起别名的感觉， 这样就可执行sql语句了
df.createTempView("students")
spark.sql("select clazz,count(1) as sumPerson from  students  group by clazz  ").show()
//      +--------+---------+
//      |   clazz|sumPerson|
//      +--------+---------+
//      |文科六班|      104|
//      |理科六班|       92|
//      |理科三班|       68|
//      |理科五班|       70|
//      |理科二班|       79|
//      |理科一班|       78|


    // TODO:     保存数据
    clazzPersonNum.write.mode("overwrite").csv("data/classPersonNum")


    // 一个输出流  配置输出流的属性
    val write: DataFrameWriter[Row] = clazzPersonNum.write
    val value: DataFrameWriter[Row] = write.mode("overwrite")

//    最后配置保存的路径以及形式

  }

}

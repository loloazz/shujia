package com.shujia.SparkSql

import org.apache.spark.sql.{DataFrame, SparkSession}

object demo02 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("df")
      .master("local")
      .getOrCreate()


    /**
     * 读取json数据
     *
     * 会自动识别json的类型
     */

    val jsonDF: DataFrame = spark.read.format("json").load("data/students.json")

    jsonDF.show()


    /**
     * 读取CSV
     * 就要指定字段名
     */
    spark.read.format("csv")
      .option("seq",",")// 指定分隔符默认是逗号
      .schema("id string ,name string ,age int ,gender string ,clazz string")   // 类型不区分大小写
      .load("data/students.txt").printSchema()


    /**
     * 链接 jdbc 构建DF
     */
    val jdbcDF: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://hadoop100:3306")
      .option("dbtable", "students.student")
      .option("user", "root")
      .option("password", "123456")
      .load()

    jdbcDF.printSchema()
    jdbcDF.show()


  }
}

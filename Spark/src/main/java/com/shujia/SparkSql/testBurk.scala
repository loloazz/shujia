package com.shujia.SparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.{explode, expr, lag}
import org.apache.spark.sql.{Column, DataFrame, Row, SparkSession, functions}
import org.apache.spark.util.LongAccumulator
import org.apache.spark.sql.expressions.Window

object testBurk {
  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
      .appName("每年按月累计收入")
      .config("spark.sql.shuffle.partitions", "1")
      .master("local")
      .getOrCreate()

    //    1、统计每个公司每年按月累计收入
    //burk,year,tsl01,tsl02,tsl03,tsl04,tsl05,tsl06,tsl07,tsl08,tsl09,tsl10,tsl11,tsl12


    import spark.implicits._
    val burkDF: DataFrame = spark.read.schema("burk string,year string,tsl01  int,tsl02 int,tsl03  int,tsl04  int,tsl05  int,tsl06  int,tsl07  int,tsl08  int,tsl09  int,tsl10  int,tsl11  int,tsl12  int").csv("data/burk.txt")
//    burkDF.show()
//
//    burkDF.createTempView("burk")
//
//    val rdd: RDD[Row] = burkDF.rdd
//
//    rdd.foreach(println)
//    val rddmap: RDD[(String, String, String)] = rdd.map(x => {
//      //      val strings: Array[String] = x.mkString(",").split(",")
//      //
//
//      val strings = x.mkString(",").split(",")
//
//      (strings(0), strings(1),
//        strings(2) + "," + strings(3) + "," + strings(4) + "," +
//          strings(5) + "," + strings(6) + "," + strings(7) + "," +
//          strings(8) + "," + strings(9) + "," + strings(10) + ","
//          + strings(11) + "," + strings(12) + ","
//          + strings(13))
//    })
//
//    rddmap.foreach(println)
//
//    val df: DataFrame = rddmap.toDF()
//    val zuheDF: DataFrame = df.toDF("gongsi", "year", "months")
//
//    val allWords = zuheDF.select($"gongsi", $"year", explode(functions.split($"months", ",")).as("month"))
//
//
//    val accumulator: LongAccumulator = spark.sparkContext.longAccumulator
//
//    val maped: RDD[(String, String, String, Long)] = allWords.rdd.map(x => {
//      accumulator.add(1)
//      val strings: Array[String] = x.mkString(",").split(",")
//
//
//      if (accumulator.value == 13) {
//        accumulator.reset()
//        accumulator.add(1)
//      }
//      (strings(0), strings(1), strings(2), accumulator.value)
//
//    })
//
//    maped.foreach(println)
//
//    val dataFrame: DataFrame = maped.toDF("gongsi", "year", "money", "month")
//    dataFrame.createTempView("table")
//    val rdd1: RDD[Row] = dataFrame.withColumn("lilixiaoshou", lag("money", 1) over (Window.partitionBy("gongsi", "year").orderBy("month")))
//      .select("gongsi", "year", "money", "month", "lilixiaoshou").rdd
//
//    val result: RDD[(String, String, String, String, Int)] = rdd1.map(x => {
//      val strings: Array[String] = x.mkString(",").split(",")
//
//      if (strings(4).equals("null")) {
//        val i2: Int = strings(2).toInt
//        (strings(0), strings(1), strings(2), strings(3), i2)
//
//      } else {
//
//        val i: Int = strings(4).toInt + strings(2).toInt
//        (strings(0), strings(1), strings(2), strings(3), i)
//
//      }
//
//    })
//    result.foreach(println)

    //    输出结果
    //    公司代码,年度,月份,当月收入,累计收入

    import  org.apache.spark.sql.functions._

    val kv: Column = map(
      expr("1"), $"tsl01",
      expr("2"), $"tsl02",
      expr("3"), $"tsl03",
      expr("4"), $"tsl04",
      expr("5"), $"tsl05",
      expr("6"), $"tsl06",
      expr("7"), $"tsl07",
      expr("8"), $"tsl08",
      expr("9"), $"tsl09",
      expr("10"), $"tsl10",
      expr("11"), $"tsl11",
      expr("12"), $"tsl12"

    )
    val dataFrame: DataFrame = burkDF.select($"burk", $"year", explode(kv) as Array("month", "pic"))

    dataFrame.cache()

    dataFrame.select($"burk", $"year",$"month",sum("pic") over(Window.partitionBy("burk","year").orderBy("month")  )as "sumScore").show()

    //    2、统计每个公司当月比上年同期增长率
    //    公司代码,年度,月度,增长率（当月收入/上年当月收入 - 1）
    dataFrame.select($"burk", $"year",$"month",$"pic",lag("pic",1,0) over(Window.partitionBy("burk","month").orderBy("year")) as "lastpic")
      .withColumn("p", round(coalesce(($"pic" / $"lastPic") - 1, expr("1.0")), 7)).show()

  }
}

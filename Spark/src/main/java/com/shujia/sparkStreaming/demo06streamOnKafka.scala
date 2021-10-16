package com.shujia.sparkStreaming

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Durations, StreamingContext}

object demo06streamOnKafka {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .appName("streamOnKafka")
      .master("local[2]")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val ssc = new StreamingContext(sc, Durations.seconds(5))




  }
}

package com.shujia.sparkStreaming

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.{SparkConf, SparkContext}

object demo01WC {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("wc")

    val sc = new SparkContext(conf)


    new StreamingContext(c)
  }
}

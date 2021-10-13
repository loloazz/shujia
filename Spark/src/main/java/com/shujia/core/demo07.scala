package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object demo07 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("group")

    val sc = new SparkContext(conf)
    val value: RDD[(String, String)] = sc.wholeTextFiles("data")

    value.foreach(println)

  }
}

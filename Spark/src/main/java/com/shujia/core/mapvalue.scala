package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object mapvalue {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("group")

    val sc = new SparkContext(conf)

    val ageRDD: RDD[(String, Int)] = sc.parallelize(List(("001", 23), ("002", 24), ("003", 25), ("004", 26)))


    val value: RDD[(String, Int)] = ageRDD.mapValues(x => {
      x * 2
    })

    value.foreach(println)
  }

}

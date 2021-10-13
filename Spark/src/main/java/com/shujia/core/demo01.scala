package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.util

object demo01 {
  def main(args: Array[String]): Unit = {

    //
    //    1、统计班级人数
    //    2、统计学生的总分

    val conf = new SparkConf().setMaster("local").setAppName("demo01")


    val Hm = new util.HashMap[String, String]()


    val sc = new SparkContext(conf)
    val line: RDD[String] = sc.textFile("data/sparkdata/students.txt")

    val key: RDD[(String, Int)] = line.map(x => {
      val words = x.split(",")

      Hm.put(words(0), words(1))

      (words(4), 1)

    })


    val sumNum: RDD[(String, Int)] = key.reduceByKey(_ + _)

    sumNum.saveAsTextFile("data/sparkOut/sumNum")


    val score: RDD[String] = sc.textFile("data/sparkdata/score.txt")
    val scoreMap: RDD[(String, Int)] = score.map(x => {
      val y = x.split(",")


      ((y(0), y(2).toInt))


    })


    val scoreResult: RDD[(String, Int)] = scoreMap.reduceByKey(_ + _)
    scoreResult.saveAsTextFile("data/sparkOut/sumscore")

  }

}

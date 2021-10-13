package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object demo03 {
  def main(args: Array[String]): Unit = {

    //    2、统计总分大于年级平均分的学生 [学号，姓名，班级，总分]
    //    1、统计总分
    //    2、统计年级平均分
    //    3、过滤总分是大于平均分
    //    4、整理数据

    val conf = new SparkConf().setMaster("local").setAppName("demo01")
    val sc = new SparkContext(conf)
    val score: RDD[String] = sc.textFile("data/sparkdata/score.txt")
    val scoreMap: RDD[(String, String)] = score.map(x => {
      val y = x.split(",")
      (y(0), y(2))
    })

    //(1500100968,(320,(谭晗日,文科五班)))
    val students: RDD[String] = sc.textFile("data/sparkdata/students.txt")

    val studentsMap: RDD[(String, (String, String))] = students.map(x => {
      val y = x.split(",")
      ((y(0), (y(1), y(4))))
    })

    val map1: RDD[(String, String, String, String)] = studentsMap.join(scoreMap).map(x => {
      (x._1, x._2._1._1, x._2._1._2, x._2._2)
    })

    //    map1.foreach(println)
    //    (1500100999,钟绮晴,文科五班,48)
    val jieduan: RDD[(String, String, String, String)] = map1.map(x => {
      (x._1, x._2, x._3.substring(0, 2), x._4)
    })

    val wenlike: RDD[(String, Iterable[(String, String, String, String)])] = jieduan.groupBy(x => {
      x._1
    })
    val map2: RDD[(String, Iterable[String], Iterable[String])] = wenlike.map(x => {
      (x._1, x._2.map(x => {
        x._3
      }), x._2.map(_._4))
    })

    val huajian = map2.map(x => {
      (x._1, x._2.take(1).toArray.mkString, x._3.map(_.toInt).sum)
    })


    //    huajian.foreach(println)

    val groupby = huajian.groupBy(x => {
      x._2
    })

    val pingjun: RDD[(String, Double)] = groupby.map(x => {
      (x._1, x._2.map(x => {
        x._3
      }).sum.toDouble / x._2.size)
    })

    val wenkepingjunfen = pingjun.filter(x => {
      x._1.equals("文科")
    }).map(x => {
      x._2
    }).max()

    val like: Double = pingjun.filter(x => {
      x._1.equals("理科")
    }).map(x => {
      x._2
    }).max()

    val result1: RDD[(String, String, Int)] = huajian.filter(x => {
      if (x._2.equals("理科")) {
        x._3 >= like
      } else {
        val dd: Int = x._3

        x._3 >= wenkepingjunfen
      }
    })

    val remap: RDD[(String, (String, Int))] = result1.map(x => {
      (x._1, (x._2, x._3))
    })
    remap.join(studentsMap).map(x => {
      (x._1, x._2._1._2, x._2._2._1, x._2._2._2)
    }).foreach(println)

  }
}

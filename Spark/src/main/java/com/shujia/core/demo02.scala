package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object demo02 {
  def main(args: Array[String]): Unit = {
    //    1、统计年级排名前十学生各科的分数 [学号,学生姓名，学生班级，科目，分数]

    val conf = new SparkConf().setMaster("local").setAppName("demo01")
    val sc = new SparkContext(conf)
    val score: RDD[String] = sc.textFile("data/sparkdata/score.txt")
    val scoreMap: RDD[(String, (String, Int))] = score.map(x => {
      val y = x.split(",")
      ((y(0), (y(1), y(2).toInt)))
    })

    //(1500100968,(320,(谭晗日,文科五班)))
    val students: RDD[String] = sc.textFile("data/sparkdata/students.txt")

    val studentsMap: RDD[(String, (String, String))] = students.map(x => {
      val y = x.split(",")
      ((y(0), (y(1), y(4))))
    })
    //    (1500100736,((钭涵亮,理科四班),(1000008,44)))
    val studentsClass: RDD[(String, ((String, String), (String, Int)))] = studentsMap.join(scoreMap)

    val zhankai: RDD[(String, (String, String, String, Int))] = studentsClass.map(x => {
      (x._2._2._1, (x._1, x._2._1._2, x._2._1._1, x._2._2._2))
    })
    //    zhankai.foreach(println)  (1500100724,文科一班,路星腾,1000001,143)

    //  zhankai.foreach(println)

    val cource: RDD[String] = sc.textFile("data/sparkdata/cource.txt")
    val coure: RDD[(String, String)] = cource.map(x => {
      val y = x.split(",")
      (y(0), y(1))
    })
    //    zhankai.join(coure).foreach(println)
    val joinCourse: RDD[(String, ((String, String, String, Int), String))] = zhankai.join(coure)

    val join_map: RDD[(String, String, String, String, Int)] = joinCourse.map(x => {
      x._2
    }).map(x => {
      (x._1._1, x._1._2, x._1._3, x._2, x._1._4)
    })

    //    join_map.foreach(println)
    val group_map: RDD[((String, String, String), Iterable[(String, String, String, String, Int)])] = join_map.groupBy(x => {
      (x._1, x._2, x._3)
    })

    group_map.foreach(println)
    val mapp: RDD[((String, String, String), Iterable[(String, Int)])] = group_map.map(x => {
      (x._1, x._2.map(x => {
        (x._4, x._5)
      }))
    })

    //    mapp.foreach(println)

    val sort: RDD[((String, String, String), Iterable[(String, Int)])] = mapp.sortBy(x => {
      x._2.map(x => {
        x._2
      }).sum
    }, false)
    val huajian = sort.map(x => {
      (x._1._1, x._1._2, x._1._3, x._2.map(x => {
        (x._1, x._2)
      }))
    }).take(10).foreach(println
    )


  }


}

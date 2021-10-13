package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object fangcha {

  /**
   * * 4、统计偏科最严重的前100名学生  [学号，姓名，班级，科目，分数
   * 偏科程度判断依据：通过方差判断偏科程度
   */


  /** *
   * 设一组数据x1,x2,x3……xn中，各组数据与它们的平均数
   * 的差的平方分别是(x1- )，(x2- )……(xn- )，
   * 那么我们用他们的平均数 来衡量这组数据的波动大小，
   * 并把它叫做这组数据的方差。为了简便 （其中x为该组数据的平均值）。
   *
   * @param args
   */

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("fangcha")
    val sc = new SparkContext(conf)


    val score: RDD[String] = sc.textFile("data/sparkdata/score.txt")

    val cource: RDD[String] = sc.textFile("data/sparkdata/cource.txt")

    val scoreMap: RDD[(String, (String, String))] = score.map(x => {
      val strings: Array[String] = x.split(",")
      (strings(1), (strings(0), strings(2)))

    })
    val courceMap: RDD[(String, Int)] = cource.map(x => {
      val strings: Array[String] = x.split(",")
      (strings(0), strings(2).toInt)

    })

    val scoreJoinCourse: RDD[(String, ((String, String), Int))] = scoreMap.join(courceMap)
    val stdFormat: RDD[(String, String, Double)] = scoreJoinCourse.map {
      case (cid: String, ((id: String, score: String), sumScore: Int)) => (id, cid, score.toDouble / sumScore)
    }
    val groupCid: RDD[(String, Iterable[(String, String, Double)])] = stdFormat.groupBy(x => x._2)
    val mapAndFlatmap: RDD[(String, String, Double)] = groupCid.map(_._2).flatMap(x => x)
    mapAndFlatmap.foreach(println)


    val group: RDD[(String, Iterable[(String, String, Double)])] = mapAndFlatmap.groupBy(x => {
      x._1
    })

    val groupSum: RDD[(String, Double)] = group.map(x => {
      (x._1, x._2.map(y => {
        y._3
      }).sum)
    })


  }

}

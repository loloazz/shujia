package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object demo06Cache {
  def main(args: Array[String]): Unit = {

    //环境配置对象
    val conf = new SparkConf()

    //指定任务名
    conf.setAppName("cache")
    conf.setMaster("local")


    //创建spark 环境,式写spark代码的入口
    val sc = new SparkContext(conf)


    val studentRDD: RDD[String] = sc.textFile("data/students.txt")


    val ageRDD: RDD[(String, Int)] = studentRDD.map(x => {
      val spilt = x.split(",")
      println("*********")
      (spilt(4), spilt(2).toInt)
    })

    //    ageRDD.cache()
//    ageRDD.persist(MEMORY_ONLY)

    //    val NONE = new StorageLevel(false, false, false, false)
    //    val DISK_ONLY = new StorageLevel(true, false, false, false)
    //    val DISK_ONLY_2 = new StorageLevel(true, false, false, false, 2)
    //    val MEMORY_ONLY = new StorageLevel(false, true, false, true)
    //    val MEMORY_ONLY_2 = new StorageLevel(false, true, false, true, 2)
    //    val MEMORY_ONLY_SER = new StorageLevel(false, true, false, false)
    //    val MEMORY_ONLY_SER_2 = new StorageLevel(false, true, false, false, 2)
    //    val MEMORY_AND_DISK = new StorageLevel(true, true, false, true)
    //    val MEMORY_AND_DISK_2 = new StorageLevel(true, true, false, true, 2)
    //    val MEMORY_AND_DISK_SER = new StorageLevel(true, true, false, false)
    //    val MEMORY_AND_DISK_SER_2 = new StorageLevel(true, true, false, false, 2)
    //    val OFF_HEAP = new StorageLevel(true, true, true, false, 1)


    //统计班级的平均年龄
    val avgAge: RDD[(String, Double)] = ageRDD.groupByKey().map(x => {
      (x._1, x._2.toArray.sum.toDouble / x._2.size)
    })

    avgAge.foreach(println)

    val peoCount: RDD[(String, Int)] = ageRDD.map(x => {
      (x._1, 1)
    })

    val groupbyClazz: RDD[(String, Iterable[Int])] = peoCount.groupByKey()

    val sumPeoson: RDD[(String, Int)] = groupbyClazz.map {
      case (clazz: String, count: Iterable[Int]) => (clazz, count.sum)
    }


    sumPeoson.foreach(println)




    //统计班级的人数


  }
}

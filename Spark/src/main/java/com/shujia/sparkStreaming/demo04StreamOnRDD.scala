package com.shujia.sparkStreaming

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}

object demo04StreamOnRDD {
  def main(args: Array[String]): Unit = {


    /**
     * 初始化环境
     */
    val spark: SparkSession = SparkSession.builder().appName("treamOnRDD").master("local[2]").getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val ssc = new StreamingContext(sc, Durations.seconds(5))


    val rcIDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop100", 8888)




    // 遍历所有流中的rdd  没有返回值
    rcIDS.foreachRDD(rdd => {
      rdd.flatMap(_.split(",")).map((_, 1)).reduceByKey(_ + _).foreach(println)
    })


    val newRDD: DStream[(String, Int)] = rcIDS.transform(RDD => {
      val ToDRR: RDD[(String, Int)] = RDD.flatMap(_.split(",")).map((_, 1))
      ToDRR


    })

    newRDD.reduceByKey(_+_).print()



    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }


}

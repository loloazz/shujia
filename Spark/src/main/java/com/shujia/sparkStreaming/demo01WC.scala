package com.shujia.sparkStreaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object demo01WC {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("wc")

    val sc = new SparkContext(conf)


    val ssc = new StreamingContext(sc, Durations.seconds(5))


    val lineDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop100", 8888)  // 创建socket文本输入流对象

    val wordDS: DStream[String] = lineDS.flatMap(x => {   // 将字符串打开
      x.split(",")
    })

    val kvDS: DStream[(String, Int)] = wordDS.map((_, 1))  // map 转换

    val reduceDS: DStream[(String, Int)] = kvDS.reduceByKey(_ + _)   // 聚合打印

    reduceDS.print()

    /**
     * 开启资源
     */
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }
}

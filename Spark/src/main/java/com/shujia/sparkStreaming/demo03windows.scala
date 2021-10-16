package com.shujia.sparkStreaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Durations, StreamingContext}

object demo03windows {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("windows ")

    val sc = new SparkContext(conf)

    /**
     * 创建spark streaming环境
     *
     */

    val ssc = new StreamingContext(sc, Durations.seconds(5))
    ssc.checkpoint("data/checkpoint")

    /**
     * 启动socket
     * yum install nc
     * nc -lk 8888
     *
     */

    //1、读取数据
    val lineDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop100", 8888)


    val wordDS: DStream[String] = lineDS.flatMap(line => line.split(","))

    val kvDS: DStream[(String, Int)] = wordDS.map((_, 1))


    /**
     * 统计最近15秒单词的数量，每隔5秒统计一次
     *
     */

    kvDS.reduceByKeyAndWindow ((x: Int, y: Int) => x + y, Durations.seconds(15), Durations.seconds(5))
      .filter(x => {
        x._2 != 0
      }).print()
    //    windowDuration – 窗口的宽度； 必须是此 DStream 的批处理间隔的倍数



    ssc.start()
    ssc.awaitTermination()
    ssc.stop()



  }
}

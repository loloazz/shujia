package com.shujia.sparkStreaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Durations, StreamingContext}


object demo06streamOnKafka {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .appName("streamOnKafka")
      .master("local[2]")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val ssc = new StreamingContext(sc, Durations.seconds(5))

    val kafkaParams: Map[String, Object] = Map[String, Object](

      "bootstrap.servers" -> "hadoop100:9092,hadoop100:9092,hadoop100:9092", //froker 列表
      "key.deserializer" -> classOf[StringDeserializer], //key 反序列化的列
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "asdasdasd", //消费者组, 同一条数据在一个组内只被消费一次
      "auto.offset.reset" -> "latest", // 消费数据的位置（latest： 读最新数据）
      "enable.auto.commit" -> (false: java.lang.Boolean) // 是否自动提交消费偏移量

    )


    ssc.checkpoint("data/checkPoint")
    val topics: Array[String] = Array("flumetokafka")

    val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams)
    )

    stream.flatMap(x => {
      x.value().split(",")

    }).map((_, 1)).updateStateByKey((seq: Seq[Int], option: Option[Int]) => {
      Some(seq.sum + option.getOrElse(0))
    }).print()

    ssc.start()


    ssc.awaitTermination()

    ssc.stop()


  }
}

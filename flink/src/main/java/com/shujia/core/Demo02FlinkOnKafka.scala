package com.shujia.core

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util.Properties


/**
 * 消费kafka的数据
 */
object Demo02FlinkOnKafka {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()

    //1、指定kafkabroker地址
    properties.setProperty("bootstrap.servers", "hadoop100:9092,hadoop101:9092,hadoop102:9092")

    //2、指定kv的序列化类
    properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


    val consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](
      "flink",
      // 指定序列化的方式
      new SimpleStringSchema()
      , properties)


    val kafkaStream: DataStream[String] = env.addSource(consumer)


    val wordCount: DataStream[String] = kafkaStream.flatMap(x => {
      x.split(",")
    }).map(y => {
      (y, 1)
    }).keyBy(x => {
      x._1
    })
      .sum(1).map(z => {
      z._1 + "\t" + z._2
    })


    wordCount.print()






    env.execute()





  }
}

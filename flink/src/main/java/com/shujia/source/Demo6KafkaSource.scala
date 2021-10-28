package com.shujia.source

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import java.util.Properties

object Demo6KafkaSource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "hadoop100:9092,hadoop101:9092,hadoop102:9092")
    properties.setProperty("group.id", "test")


    //创建消费者
    val flinkKafkaConsumer = new FlinkKafkaConsumer[String](
      "flink",
      new SimpleStringSchema(),
      properties
    )

    //指定读取数据的位置
    flinkKafkaConsumer.setStartFromEarliest() // 尽可能从最早的记录开始
    //    flinkKafkaConsumer.setStartFromLatest() // 从最新的记录开始
    //    flinkKafkaConsumer.setStartFromTimestamp(...) // 从指定的时间开始（毫秒）
    //    flinkKafkaConsumer.setStartFromGroupOffsets() // 默认的方法，从消费者组读取数据，如果消费者组不存在读取最新数据


    //使用kafkasource 构建DataStream

    val kafkaDS: DataStream[String] = env.addSource(flinkKafkaConsumer)


    kafkaDS.print()


    env.execute()

  }

}

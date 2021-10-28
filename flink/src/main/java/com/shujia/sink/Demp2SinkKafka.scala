package com.shujia.sink

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object Demp2SinkKafka {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment


    val studentDS: DataStream[String] = env.readTextFile("data/students.txt")


    //生产者
    val flinkKafkaProducer = new FlinkKafkaProducer[String](
      "master:9092,node1:9092,node2:9092", // broker 列表
      "flink.student", // 目标 topic
      new SimpleStringSchema()) // 序列化 schema


    //将数据sink到kafak
    studentDS.addSink(flinkKafkaProducer)


    env.execute()

  }

}

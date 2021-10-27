package com.shujia.core


// 注意导入的依赖

import org.apache.flink.streaming.api.scala._

object Demo01WC {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    // 使用socket进行流计算   yum    nc -lk 8888
    val line: DataStream[String] = env.socketTextStream("hadoop100", 8888)

    val flatmapWords: DataStream[String] = line.flatMap(x => {
      x.split(",")
    })

    val mapWord: DataStream[(String, Int)] = flatmapWords.map(x => {
      (x, 1)
    })

    mapWord.keyBy(x=>{x._1}).sum(1).print()


    env.execute()
  }
}

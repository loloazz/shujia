package com.shujia.source

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._

object demo1MySource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val DIYSource: DataStream[Int] = env.addSource(new mySource)

    DIYSource.print()

    env.execute()

  }
}

class mySource extends  SourceFunction[Int] {
  /**
   * ctx 是可以将数据发送到下游
   * @param ctx
   */
  override def run(ctx: SourceFunction.SourceContext[Int]): Unit = {
  var  i = 0
    while (true){

      ctx.collect(i)
      Thread.sleep(1000)
      i+=1;


    }

  }

  override def cancel(): Unit = {


  }
}

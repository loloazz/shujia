package com.shujia.sparkStreaming

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}

object Demo2UpdateStateByKey {
  /**
   * 该类是实时更新算子的中数据的状态
   *
   *
   */

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[2]")
      .appName("UpdateStateByKey")
      .getOrCreate()

    /**
     * 使用spark得到sparkContext对象
     */
    val sc: SparkContext = spark.sparkContext
    val ssc = new StreamingContext(sc, Durations.seconds(5))


    /**
     * 两者效果一致
     */
    //    sc.setCheckpointDir("data/checkPoint")  // 必须设置checkpoint

    ssc.checkpoint("data/checkPoint")

    /**
     * ssc.checkpoint 其实还是调用sc.setcheckPoint 方法  只不过是完善一下
     */
    //  def checkpoint(directory: String) {
    //    if (directory != null) {
    //      val path = new Path(directory)
    //      val fs = path.getFileSystem(sparkContext.hadoopConfiguration)
    //      fs.mkdirs(path)
    //      val fullPath = fs.getFileStatus(path).getPath().toString
    //      sc.setCheckpointDir(fullPath)
    //      checkpointDir = fullPath
    //    } else {
    //      checkpointDir = null
    //    }
    //  }

    val lineDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop100", 8888)  // 创建socket文本输入流对象

    val wordDS: DStream[String] = lineDS.flatMap(x => {   // 将字符串打开
      x.split(",")
    })


    val KV: DStream[(String, Int)] = wordDS.map((_, 1))

    KV.foreachRDD(println(_))


    //

    def  updateState(seq:Seq[Int], option: Option[Int]) : Option[Int]={

      val last: Int = option.getOrElse(0)
      val now: Int = seq.sum

      Some(last+now)

    }


    val result: DStream[(String, Int)] = KV.updateStateByKey(updateState)

    result.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()


  }
}

package com.shujia.sparkStreaming

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SPARK_REVISION, SparkContext}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.dstream.ReceiverInputDStream

object demo05StreamOnDF {
  def main(args: Array[String]): Unit = {

    // DS  -->  RDD -->DF

    val spark: SparkSession = SparkSession
      .builder()
      .appName("treamOnRDD")
      .master("local[2]")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val ssc = new StreamingContext(sc, Durations.seconds(5))


    val rcIDS: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop100", 8888)


    import spark.implicits._

    import org.apache.spark.sql.functions._


    rcIDS.foreachRDD(RDD => {

      val HCONF = new Configuration()
      val fs: FileSystem = FileSystem.get(HCONF)


      val flag: Boolean = fs.exists(new Path("data/ds_on_df"))
      if (!flag) {
        fs.create(new Path("data/ds_on_df"))
      }


      val lastDF: DataFrame = spark
        .read
        .format("csv")
        .schema("word STRING,c LONG")
        .load("data/ds_on_df")

      val line: DataFrame = RDD.toDF("line")

      line.createOrReplaceTempView("words")

      val nowLine: DataFrame = spark.sql(
        """
          |select word,count(1)as c from (
          |select explode(split(line,',')) as word from words
          |)  as a
          |group by word
          |""".stripMargin)


      val unionDF: DataFrame = nowLine.union(lastDF).groupBy($"word").agg(sum($"c"))
      unionDF.show()

      //保存数据

      unionDF.write.mode(SaveMode.Overwrite).csv("data/ds_on_df_temp")

      //删除之前的
      if (fs.exists(new Path("data/ds_on_df"))) {
        fs.delete(new Path("data/ds_on_df"), true)
      }
      fs.rename(new Path("data/ds_on_df_temp"), new Path("data/ds_on_df"))


    })


    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }
}

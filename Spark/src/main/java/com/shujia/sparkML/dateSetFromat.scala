package com.shujia.sparkML

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}



object dateSetFromat {
  def main(args: Array[String]): Unit = {
   // 对训练数据进行处理
    //USER_ID,FLOW,FLOW_LAST_ONE,FLOW_LAST_TWO,MONTH_FEE,MONTHS_3AVG,BINDEXP_DATE,PHONE_CHANGE,AGE,OPEN_DATE,REMOVE_TAG

    val conf: SparkConf = new SparkConf().setAppName("数据处理").setMaster("local")
    val sc = new SparkContext(conf)

//月流量，上一月流量，上两个月流量，当月收入，最近3个月平均收入 这些列来预测 用户的状态。
    //实现一个简单的二值分类器对数据进行训练

    val lines: RDD[String] = sc.textFile("data/ML/训练集.csv")
    val value: RDD[(String, String, String, String, String, String, String)] = lines.map(line => {

      val strings: Array[String] = line.split(",")
      if (strings(10).mkString.equals("REMOVE_TAG")){

        (strings(0), strings(1), strings(2), strings(3), strings(4), strings(5), "tag")
      } else if (strings(10).mkString.equals("A")) {

        val tag = ",1"
        // 在这里保留USER_ID,FLOW,FLOW_LAST_ONE,FLOW_LAST_TWO,MONTH_FEE,MONTHS_3AVG ，并转换字段
        (strings(0), strings(1), strings(2), strings(3), strings(4), strings(5), "1")
      } else {

        (strings(0), strings(1), strings(2), strings(3), strings(4), strings(5), "0")
      }


    })

    value.saveAsTextFile("data/sparkOut")



  }
}

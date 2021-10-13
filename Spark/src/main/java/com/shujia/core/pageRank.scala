package com.shujia.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object pageRank {
  /*
  *
  * 每个页面设置相同的PR值
  Google的PageRank算法给每个页面的PR初始值为1
  *
  * 迭代递归计算（收敛）
  *
  * Google不断的重复计算每个页面的PageRank。那么经过不断的重复计算，这些页面的PR值会趋向于稳定，也就是收敛的状态。
  在具体企业应用中怎么样确定收敛标准？
  每个页面的PR值和上一次计算的PR相等
  设定一个差值指标（0.0001）。当所有页面和上一次计算的PR差值平均小于该标准时，则收敛。
  设定一个百分比（99%），当99%的页面和上一次计算的PR相等
  *
  * 由于存在一些出链为0，也就是那些不链接任何其他网页的网，
  * 也称为孤立网页，使得很多网页能被访问到。
  * 因此需要对 PageRank公式进行修正。
  * 即在简单公式的基础上增加了阻尼系数（damping factor）q， q一般取值q=0.85。
  * pr = 1-q/n + 累加和（pagerank）
  *
  *
  * A - B,D
  * C - A,B
  * B - C
  * D - B,D
  *
  * */
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("pageRank")
    val sc = new SparkContext(conf)

    val data: RDD[String] = sc.textFile("data/sparkdata/pr")
    // TODO: 转换
    //  PR初始值为1
    val gg: RDD[(String, (Double, List[String]))] = data.map(x => {
      val key: Array[String] = x.split("-")
      (key(0), (1.0, key(1).split(",").toList))
    })

    //网页数量
    val N = gg.count()
    //阻尼系数
    val q = 0.85

    var input = gg

    while (true) {
      val prfenpei = input.flatMap {
        case (pg: String, (pr: Double, rage: List[String])) =>
          rage.map(x => (x, pr / rage.size))

      }
      //(B,0.5)
      val newpr: RDD[(String, Double)] = prfenpei.reduceByKey(_ + _)

      // 整理数据


      val newpeJoin: RDD[(String, (Double, (Double, List[String])))] = newpr.map(x => {
        (x._1, (1 - q) / N + q * x._2)
      }).join(input)

      ///(B,(1.5,(1.0,List(C))))
      val prrr: RDD[(String, (Double, List[String]))] = newpeJoin.map {
        case (page: String, (npr: Double, (old: Double, ra: List[String]))) =>
          //pg: String, (pr: Double, rage: List[String]
          (page, (npr, ra))
      }
      prrr.foreach(println)
      //      设定一个差值指标（0.0001）。当所有页面和上一次计算的PR差值平均小于该标准时，则收敛。
      if (newpeJoin.map {
        case (page: String, (npr: Double, (old: Double, ra: List[String]))) =>
          math.abs(old - npr)
      }.sum() / N < 0.0001) {
        return
      }

      input = prrr

    }
  }

}

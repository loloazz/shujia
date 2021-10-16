package com.shujia.sparkML

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.functions.{count, sum, when}
object dasai {
  def main(args: Array[String]): Unit = {

    // 配置环境
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("ml")
      .getOrCreate()

    import spark.implicits._


    // 加载数据

    val dataFrame: DataFrame = spark.read.format("csv").option("header", true).load("data/ML/训练集.csv")


//    dataFrame.printSchema()

    val dataset: Dataset[(Double, linalg.Vector)] = dataFrame.map(data => {

      val FLOW: Double = data.getAs[String]("FLOW").toDouble
      val FLOW_LAST_ONE: Double = data.getAs[String]("FLOW_LAST_ONE").toDouble
      val FLOW_LAST_TWO: Double = data.getAs[String]("FLOW_LAST_TWO").toDouble
      val MONTH_FEE: Double = data.getAs[String]("MONTH_FEE").toDouble
      val MONTHS_3AVG: Double = data.getAs[String]("MONTHS_3AVG").toDouble

      val label: Double = data.getAs[String]("REMOVE_TAG") match {
        case "A" => 1.0
        case _ => 0.0
      }


      // 构造向量  用数组保存起来
      val vector: linalg.Vector = Vectors.dense(Array(FLOW, FLOW_LAST_ONE, FLOW_LAST_TWO, MONTH_FEE, MONTHS_3AVG))
      (label, vector)
    })

    // 转成df
    val df: DataFrame = dataset.toDF("label", "features")
    df.show()

    // 划分数据

    val splitArrary: Array[Dataset[Row]] = df.randomSplit(Array(0.8, 0.2))

    val taindata: Dataset[Row] = splitArrary(0)

    val testdata: Dataset[Row] = splitArrary(1)


    //  创建逻辑回归

    val regression: LogisticRegression = new LogisticRegression().setMaxIter(20).setFitIntercept(true)


    //开始训练

    val tainmode: LogisticRegressionModel = regression.fit(taindata)



    //铜鼓测试数据测试模型的准确率

    val result: DataFrame = tainmode.transform(testdata)

    result.show(false)

//    //计算准确率
//    result
//      .select(sum(when($"lable" === $"prediction", 1).otherwise(0)) / count($"lable"))
//      .show()

    result.select(sum($"label"===$"prediction"))

    tainmode.save("data/ml/tain.model")

  }
}

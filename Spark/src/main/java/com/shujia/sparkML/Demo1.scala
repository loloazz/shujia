package com.shujia.sparkML

import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.functions.{count, sum, when}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Demo1 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local")
      .appName("ml")
      .getOrCreate()

    import spark.implicits._


    val data: DataFrame = spark.read
      .format("csv")
      .option("header", "true")
      .load("data/ML/训练集.csv")

    data.printSchema()

    val data2: Dataset[(Double, linalg.Vector)] = data.map(row => {

      val FLOW_LAST_ONE: Double = row.getAs[String]("FLOW_LAST_ONE").toDouble
      val AGE: Double = row.getAs[String]("AGE").toDouble
      val MONTHS_3AVG: Double = row.getAs[String]("MONTHS_3AVG").toDouble
      val FLOW_LAST_TWO: Double = row.getAs[String]("FLOW_LAST_TWO").toDouble
      val MONTH_FEE: Double = row.getAs[String]("MONTH_FEE").toDouble

      val REMOVE_TAG: String = row.getAs[String]("REMOVE_TAG")

      val label: Double = REMOVE_TAG match {
        case "A" => 1.0
        case _ => 0.0
      }

      val vector: linalg.Vector = Vectors.dense(Array(FLOW_LAST_ONE, AGE, MONTHS_3AVG, FLOW_LAST_TWO, MONTH_FEE))


      Tuple2(label, vector)
    })

    val data3: DataFrame = data2.toDF("label","features")



    //切分数据集

    val array: Array[Dataset[Row]] = data3.randomSplit(Array(0.8,0.2))

    val trainData: Dataset[Row] = array(0)
    val textData: Dataset[Row] = array(1)



    //构建算法指定参数
    val logisticRegression: LogisticRegression = new LogisticRegression()
      .setMaxIter(20)
      .setFitIntercept(true)


    //将训练集带入算法训练模型
    //底层使用spark 进行分布式模型训练
    val model: LogisticRegressionModel = logisticRegression.fit(trainData)



    //铜鼓测试数据测试模型的准确率

    val result: DataFrame = model.transform(textData)

    result.show(false)
//
//    //计算准确率
//    result
//      .select(sum(when($"label" === $"prediction", 1).otherwise(0)) / count($"label"))
//      .show()

    model.save("data.model")

    //加载模型
    //val model1: LogisticRegressionModel = LogisticRegressionModel.load("data.model")



  }

}

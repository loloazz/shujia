package com.shujia.sparkML


import org.apache.spark.sql.SparkSession

object summary {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local")
      .appName("sql")
      .config("spark.sql.shuffle.partitions", 1)
      .getOrCreate()

    val irisDF = spark.read
      .options(Map("header" -> "true",
        "nullValue" -> "0",
        "inferSchema" -> "true")).csv("data/ML/训练集.csv")
    irisDF.describe().show()




  }


}

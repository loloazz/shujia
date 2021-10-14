package com.shujia.SparkSql

import org.apache.spark.sql.{DataFrame, SparkSession}

//导入spark 所有的函数

import  org.apache.spark.sql.functions._

object demo03 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local")
      .appName("df")
      .config("spark.sql.shuffle.partitions", "1") //spark sql shuffle之后分区数据，默认值是200
      .getOrCreate()


    val students: DataFrame = spark.read.format("csv")
      .option("sep", ",") //指定数据分隔方式，默认是逗号
      .schema("id string ,name string ,age int ,gender string,clazz string ")
      .load("data/students.txt")
    //    show 相当于action算子 ，查看数据   默认20条

    /**
     * select 选择字段
     */
    students.select("id", "clazz").show()

    //    +----------+--------+
    //|        id|   clazz|
    //+----------+--------+
    //|1500100001|文科六班|


    //使用列对象的方式

    import spark.implicits._
    //    as 起别名
    students.select($"id", $"age" + 1 as "age").show()
    //+----------+---+
    //|        id|age|
    //+----------+---+
    //|1500100001| 23|
    //|1500100002| 25|
    //|1500100003| 23|
    //|1500100004| 25|
    //|1500100005| 23|

    /** *
     * where
     */
    //小于等于
    students.where($"age" <= 22).show()

    //等于 ===
    students.where($"age" === 24).show()

    // 不等于  =!=
    students.where($"age" =!= 24).show()
    // 多条件   用and链接 即可   也可接着链式调用
    students.where($"age" =!= 24 and $"gender" === "男").show()


    /**
     * group 聚合
     *
     */

    students
      .groupBy("clazz")
      .avg("age")
      .show()
    //+--------+------------------+
    //|   clazz|          avg(age)|
    //+--------+------------------+
    //|文科六班| 22.60576923076923|
    //|理科六班| 22.48913043478261|


    students.groupBy("gender").agg(avg("age") as "avgAge").show()

    //+------+-----------------+
    //|gender|           avgAge|
    //+------+-----------------+
    //|    女|22.50101419878296|
    //|    男|22.54043392504931|
    //+------+-----------------+


    /**
     * 多列分组
     */

    students
      .groupBy($"clazz",$"gender")
      .agg(avg("age") as "avg")
      .show()
    //+--------+------+------------------+
    //|   clazz|gender|               avg|
    //+--------+------+------------------+
    //|文科六班|    女|22.632653061224488|
    //|文科六班|    男|22.581818181818182|
    //|理科六班|    女| 22.54054054054054|
    //|理科三班|    男| 22.82857142857143|
    //|理科五班|    女| 22.64864864864865|
    //|理科二班|    男| 22.47222222222222|


    /**
     * join
     */

//    分数表
    spark.read.format("csv")
      .option("sep",",")
      .schema("sid string,couid string ,sco int")

  }

}

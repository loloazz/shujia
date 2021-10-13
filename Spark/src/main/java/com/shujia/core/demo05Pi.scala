package com.shujia.core

import scala.collection.immutable
import scala.io.StdIn
import scala.util.Random

object demo05Pi {
  def main(args: Array[String]): Unit = {
    /** *
     * 使用随机打点法进行计算pi的值
     *
     *
     * 一个正方形的内切圆，通过判断圆的点数，与正方形的点数的比值
     * 圆 面积    = pi * r *r
     * 正方形     =  4 * r * r
     * 圆面积/正方形的面积   = pi / 4
     * pi  =   圆点数 / 正方形点数   *4
     *
     * 规定  正方向的边长为   2   那么半径就为  1
     */

    val sumPoints = StdIn.readInt()
    val list: Range.Inclusive = 0 to sumPoints

    val points: immutable.IndexedSeq[(Float, Float)] = list.map(x => {

      val rx: Float = Random.nextFloat() // 返回  0 - 1
      val ry: Float = Random.nextFloat()
      val xx = (rx * 2) - 1
      val yy = (ry * 2) - 1
      // 随机一定数目的点

      (xx, yy)
    })

    val recPoints: immutable.IndexedSeq[(Float, Float)] = points.filter(x => {

      x._1 * x._1 + x._2 * x._2 <= 1
    })


    points.map(println)

    println(s"pi的值为：${recPoints.toArray.length.toDouble / sumPoints * 4}")
  }


}

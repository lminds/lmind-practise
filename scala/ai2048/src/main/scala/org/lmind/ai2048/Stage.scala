package org.lmind.ai2048

import scala.collection.mutable.StringBuilder

class Stage(val data: Map[(Int, Int), Int]) {

  def up = {

    val builder = Map.newBuilder[(Int, Int), Int]

    builder ++= combine(List((0, 0), (0, 1), (0, 2), (0, 3)))
    builder ++= combine(List((1, 0), (1, 1), (1, 2), (1, 3)))
    builder ++= combine(List((2, 0), (2, 1), (2, 2), (2, 3)))
    builder ++= combine(List((3, 0), (3, 1), (3, 2), (3, 3)))

    new Stage(builder.result())
  }

  def down = {

    val builder = Map.newBuilder[(Int, Int), Int]

    builder ++= combine(List((0, 3), (0, 2), (0, 1), (0, 0)))
    builder ++= combine(List((1, 3), (1, 2), (1, 1), (1, 0)))
    builder ++= combine(List((2, 3), (2, 2), (2, 1), (2, 0)))
    builder ++= combine(List((3, 3), (3, 2), (3, 1), (3, 0)))

    new Stage(builder.result())
  }

  def left = {

    val builder = Map.newBuilder[(Int, Int), Int]

    builder ++= combine(List((0, 0), (1, 0), (2, 0), (3, 0)))
    builder ++= combine(List((0, 1), (1, 1), (2, 1), (3, 1)))
    builder ++= combine(List((0, 2), (1, 2), (2, 2), (3, 2)))
    builder ++= combine(List((0, 3), (1, 3), (2, 3), (3, 3)))

    new Stage(builder.result())
  }
  
  def right = {

    val builder = Map.newBuilder[(Int, Int), Int]

    builder ++= combine(List((3, 0), (2, 0), (1, 0), (0, 0)))
    builder ++= combine(List((3, 1), (2, 1), (1, 1), (0, 1)))
    builder ++= combine(List((3, 2), (2, 2), (1, 2), (0, 2)))
    builder ++= combine(List((3, 3), (2, 3), (1, 3), (0, 3)))

    new Stage(builder.result())
  }
  
  def newBlock(block:((Int, Int), Int)) = {
    val builder = Map.newBuilder[(Int, Int), Int]
    builder ++= data
    builder += block
    new Stage(builder.result())
  }
  
  def emptys = {
    data.filter(o => o._2 == 0)
  }
  
  def blocks = {
    data.filter(o => o._2 != 0)
  }
  
  def isSame(stage:Stage) = {
    data.equals(stage.data)
  }

  def combine(list: List[(Int, Int)]) = {
    val builder = List.newBuilder[((Int, Int), Int)]
    val size = list.length

    var prev: (Int, Int) = list(0)
    var got = false
    list.takeRight(list.length - 1).foreach(o => {
      if (got) {
        builder += ((prev, data(o)))
      } else if (data(prev) == data(o)) {
        builder += ((prev, data(prev) * 2))
        got = true
      } else {
        builder += ((prev, data(prev)))
      }
      prev = o
    })

    if (got) {
      builder += ((prev, 0))
    } else {
      builder += ((prev, data(prev)))
    }

    builder.result()
  }

  def print = {
    for (y <- 0 to 3) {
      val sb = new StringBuilder
      for (x <- 0 to 3) {
        val d = data((x, y))
        sb ++= d.toString + "\t"
      }
      println(sb.toString)
    }
  }
}
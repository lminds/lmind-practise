package org.lmind.io

import scala.collection.Iterator

trait CanBackIterator[A] extends Iterator[A] {

  def back(count: Int): Unit

  def peek: A

  def prev(count: Int): A
}
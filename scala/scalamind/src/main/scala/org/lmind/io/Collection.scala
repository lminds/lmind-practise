package org.lmind.io

import scala.collection.Iterator
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

object Collection {

  /**
   * 
   */
  def asCanBackIterator[A](iter: Iterator[A], backLimit: Int) = {

    new CanBackIterator[A] {

      // 迭代时的历史记录,以便用于回溯,越近的排在越前面
      private val history = ListBuffer[A]()
      if (iter.hasNext) {
        history += iter.next
      }

      // 如果进入了回溯模式，表示当前元素在history中的位置
      private var index = 0

      // 被包装的迭代器是否已经结束
      private var atEnd = false

      def hasNext: Boolean = index != -1

      def next(): A = {
        val result = peek
        index -= 1

        // 历史空间已经遍历完了,需要从iter填充数据到history
        if (index < 0) {
          if (iter.hasNext) {
            if (history.length > backLimit) {
              history.remove(history.length - 1)
            }
            history.insert(0, iter.next)
            index += 1
          }
        }

        return result
      }

      def back(count: Int): Unit = {
        index = index + count
      }

      def peek: A = history(index)
      
      def prev(count: Int): A = history(index + count)
    }
  }
}
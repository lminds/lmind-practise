package org.lmind.io

import java.{io => jio}
import scala.io.Codec
import scala.io.BufferedSource
import scala.util.parsing.input.Reader

object Conversions {
  
  def asBufferedSource(input:jio.InputStream, codec:Codec) = new BufferedSource(input)(codec)

  implicit def asIterable(reader:jio.Reader) = {
    new Iterator[Char] {
      
      private var cur = reader.read()
      
      def hasNext: Boolean = cur != -1
      
      def next(): Char = {
        val r = cur
        cur = reader.read()
        r.toChar
      }
    }
  }
  
  implicit def asIterable[T](reader:Reader[T]) = {
    var r = reader
    new Iterator[T] {
      def hasNext: Boolean = !r.atEnd
      def next(): T = {
        val result = r.first
        r = r.rest
        result
      }
    }

  }

}
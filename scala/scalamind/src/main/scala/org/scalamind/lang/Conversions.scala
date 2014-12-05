package org.scalamind.lang

import java.util.function.Consumer

object Conversions {

  implicit def consumerAsScala[T](reader: Consumer[T]) = {
    (a: T) =>
      {
        reader.accept(a)
      }
  }

  implicit def scalaAsConsumer[T](fn: (T) => Any) = {
    new Consumer[T] {
      def accept(t: T) = {
        fn(t)
      }
    }
  }
}
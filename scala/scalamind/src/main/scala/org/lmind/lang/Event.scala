package org.lmind.lang

trait Event[T, A] {

  def +=(listener: (T, A) => Unit): Unit

  def -=(listener: (T, A) => Unit): Unit

  def fire(sender: T, args: A): Unit
}
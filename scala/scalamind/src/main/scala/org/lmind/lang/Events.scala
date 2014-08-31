package org.lmind.lang
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

object Events {

  private class EventImpl[T, A] extends Event[T, A] {

    private var events:EventDispatcher[(T, A)=>Unit] = new EventDispatcher()

    private var _locked = new AtomicBoolean(false)

    def +=(listener: (T, A) => Unit): Unit = {
      events += listener
    }

    def -=(listener: (T, A) => Unit): Unit = {
      events -= listener
    }

    def fire(sender: T, args: A): Unit = {
      events.dispatch {
        _(sender, args)
      }
    }
  }

  def event[T, A <: EventArgs](): Event[T, A] = {
    new EventImpl
  }
}
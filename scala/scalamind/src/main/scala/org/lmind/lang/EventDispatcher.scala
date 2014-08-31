package org.lmind.lang
import java.util.List
import java.util.ArrayList
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

class EventDispatcher[T] {

  private var events = new ConcurrentLinkedQueue[T]

  private var _locked = new AtomicBoolean(false)

  def +=(listener: T) = {
    add(listener)
  }

  def -=(listener: T) = {
    remove(listener)
  }

  def add(listener: T): Boolean = {
    _locked.synchronized {
      if (_locked.get()) {
        return false
      } else {
        events.add(listener)
      }
    }
  }

  def remove(listener: T): Boolean = {
    _locked.synchronized {
      if (_locked.get()) {
        return false
      } else {
        events.remove(listener)
      }
    }
  }

  def clear() = {
    events.clear()
  }

  def lock() = {
    _locked.set(true)
  }

  def unlock() = {
    _locked.set(false)
  }

  def dispatch(disp: (T) => Unit) = {
    var array = _locked.synchronized {
      events.toArray()
    }
    for (i <- array) {
      disp(i.asInstanceOf[T])
    }
  }

  def shallowClone = {
    var other = new EventDispatcher[T]
    other.events = new ConcurrentLinkedQueue(events)
    other
  }
}
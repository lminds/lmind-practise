package org.lmind.thread
import scala.actors.threadpool.locks.ReentrantLock
import scala.actors.threadpool.TimeUnit
import java.util.Date

class StateLock[T](initState: T) {

  private var _lock = new ReentrantLock

  private var _cond = _lock.newCondition()

  private var _state: T = initState

  def state = _state
  
  def state_=(v: T) = {
    _lock.lock()
    try {
      _state = v
      _cond.signalAll()
    } finally {
      _lock.unlock()
    }
  }

  def await(expect: T, timeout: Long): Boolean = {
    await((t: T) => { t == expect }, timeout)
  }

  def await(expect: (T) => Boolean, timeout: Long): Boolean = {
    var deadline = new Date(System.currentTimeMillis + timeout)
    _lock.lock()
    try {
      while (!expect(_state)) {
        if (timeout <= 0) {
          _cond.await()
        } else {
          if (!_cond.awaitUntil(deadline)) {
            return false
          }
        }
      }
      true
    } finally {
      _lock.unlock()
    }
  }
}
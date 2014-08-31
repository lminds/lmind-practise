package org.lmind.lang

class Lock {

  def await(timeout: Long, test: () => Boolean): Boolean = {
    this.synchronized {
      var now = System.currentTimeMillis()
      while (!test()) {
        this.wait(100)
        if (timeout != -1 && System.currentTimeMillis() - now > timeout)
          false
      }
      true
    }
  }

  def await(test: () => Boolean): Boolean = {
    await(-1, test)
  }

  def doubleCheck(expr: () => Boolean, success: () => Unit = null) = {
    if (expr()) {
      this.synchronized {
        if (expr()) {
          if (success != null) {
            success()
          }
          true
        }
      }
    }
    false
  }
}
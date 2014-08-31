package org.lmind.thread

object Threads {

  def await(test: => Boolean): Boolean = {
    await(-1, test)
  }

  def await(timeout: Long, test: => Boolean): Boolean = {
    this.synchronized {
      var now = System.currentTimeMillis()
      while (!test) {
        this.wait(100)
        if (timeout != -1 && System.currentTimeMillis() - now > timeout)
          false
      }
      true
    }
  }

  def run(task: => Unit) = {
    var t = new Thread(new Runnable{
      def run = {
        task
      }
    })
    t.start()
    t
  }
}
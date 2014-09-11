package org.lmind.ai2048.voter

import org.lmind.ai2048.Stage

class BlockCountVoter {

  def vote(stage:Stage) = {
    val size = stage.emptys.size
    1 - (size * size).toDouble / 256
  }
}
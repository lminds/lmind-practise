package org.lmind.ai2048.voter

import org.lmind.ai2048.Stage

class BlockCountVoter {

  def vote(stage:Stage) = {
    stage.emptys.size.toDouble
  }
}
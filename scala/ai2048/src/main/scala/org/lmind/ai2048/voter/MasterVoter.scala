package org.lmind.ai2048.voter

import org.lmind.ai2048.Stage

class MasterVoter {

  def vote(stage:Stage) = {
    new BlockCountVoter().vote(stage)
  }
}
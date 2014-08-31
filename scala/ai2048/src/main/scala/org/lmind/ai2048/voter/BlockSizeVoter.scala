package org.lmind.ai2048.voter

import org.lmind.ai2048.Stage

class BlockSizeVoter {

  def vote(stage:Stage) = {
    stage.blocks.foldLeft(0)((a, b) => {b._2 * b._2}).toDouble
  }
}
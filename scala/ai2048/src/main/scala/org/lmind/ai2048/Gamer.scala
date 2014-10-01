package org.lmind.ai2048

import org.lmind.ai2048.voter.TreeNode
import java.util.HashMap.TreeNode
import org.lmind.ai2048.voter.MasterVoter

class Gamer {

  private var _currentRoot: TreeNode[Stage] = null

  private var state: HistoryState = null

  private val maxDeep = 2

  def init = {
    val data = Map[(Int, Int), Int](
      (0, 0) -> 0, (1, 0) -> 0, (2, 0) -> 0, (3, 0) -> 0,
      (0, 1) -> 0, (1, 1) -> 2, (2, 1) -> 0, (3, 1) -> 0,
      (0, 2) -> 0, (1, 2) -> 0, (2, 2) -> 0, (3, 2) -> 0,
      (0, 3) -> 0, (1, 3) -> 0, (2, 3) -> 0, (3, 3) -> 0)
    val stage = new Stage(data)
    
    _currentRoot = new TreeNode(stage)
    state = new HistoryState(stage, false, null)
  }
  
  def think = {
    
  }

  def run = {
    while (true) {

      thisStep

      thatStep
    }
  }

  def thisStep = {

    thinkingThis(_currentRoot, 0)

    val t = new TreeNode[Stage](null)
    t.score = 0

    val best = _currentRoot.leafs.foldLeft(t)((a, b) => {
      if (a.score > b.score) a
      else b
    })

    // 找到
    val step = best.parents.find(o => o.parent.parent == _currentRoot)
    _currentRoot = step.get
  }

  def thatStep = {
    val pos = _currentRoot.data.emptys.head._1
    _currentRoot = new TreeNode[Stage](_currentRoot.data.newBlock((pos), 2))
  }

  def thinkingThis(node: TreeNode[Stage], deep: Int): Unit = {

    thinkingThis2(node, new TreeNode(node.data.up), deep)
    thinkingThis2(node, new TreeNode(node.data.down), deep)
    thinkingThis2(node, new TreeNode(node.data.left), deep)
    thinkingThis2(node, new TreeNode(node.data.right), deep)
  }

  def thinkingThat(node: TreeNode[Stage], deep: Int): Unit = {

    node.data.emptys.foreach(o => {
      val child = new TreeNode(node.data.newBlock((o._1), 2))
      child.score = new MasterVoter().vote(child.data)

      node.appendChild(child)
      thinkingThis(child, deep)
    })

  }

  def thinkingThis2(node: TreeNode[Stage], child: TreeNode[Stage], deep: Int) = {
    if (!child.data.isSame(node.data)) {
      child.score = new MasterVoter().vote(child.data)

      node.appendChild(child)

      if (deep <= maxDeep) {
        thinkingThat(child, deep + 1)
      }
    }
  }
}
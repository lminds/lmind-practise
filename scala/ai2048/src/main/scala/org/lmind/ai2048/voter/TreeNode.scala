package org.lmind.ai2048.voter

import scala.collection.mutable._

class TreeNode[T](val data:T) {
  
  private var _parent:TreeNode[T] = null

  private var _children:ListBuffer[TreeNode[T]] = ListBuffer()
  
  var score = -1d
  
  def parent = _parent
  
  def leaf = _children.size == 0
  
  def leafs = {
    trees.filter(o => o.leaf)
  }
  
  def trees = {
    val b = List.newBuilder[TreeNode[T]]
    this.foreach(o => {
      b += o
    })
    b.result()
  }
  
  def parents = {
    var t = parent
    val b = List.newBuilder[TreeNode[T]]
    while (t != null) {
      b += t
      t = t.parent
    }
    b.result()
  }
  
  def foreach(fn:(TreeNode[T]) => Unit):Unit = {
    fn(this)
    _children.foreach(o => {
      o.foreach(fn)
    })
  }
  
  def appendChild(node:TreeNode[T]):Unit = {
    if (node == null) return
    _children.append(node)
    node._parent = this
  }
  
  def removeChild(node:TreeNode[T]):Unit = {
    val index = _children.indexOf(node)
    _children.remove(index)
  }
}
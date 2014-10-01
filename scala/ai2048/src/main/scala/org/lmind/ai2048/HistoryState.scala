package org.lmind.ai2048

class HistoryState(val stage:Stage, val thisTurn:Boolean, val op:String) {

  private var _parent:HistoryState = null
  
  private var _children:List[HistoryState] = null
  
}
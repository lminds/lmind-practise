package org.lmind.ai2048

object Test extends App {

  def main() = {
    val data = Map(
      (0, 0) -> 2, (1, 0) -> 2, (2, 0) -> 2, (3, 0) -> 2,
      (0, 1) -> 2, (1, 1) -> 4, (2, 1) -> 2, (3, 1) -> 4,
      (0, 2) -> 2, (1, 2) -> 4, (2, 2) -> 16, (3, 2) -> 6,
      (0, 3) -> 2, (1, 3) -> 2, (2, 3) -> 16, (3, 3) -> 2)

      data((3, 3))
    val stage = new Stage(data)
    
//    stage.print
    
    val s2 = stage.right.newBlock(((0, 0), 16))
    s2.print
  }

  main()
}
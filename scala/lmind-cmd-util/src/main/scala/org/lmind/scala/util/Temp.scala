package org.lmind.scala.util

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

class ManagerActor extends Actor {

  val worker = context.system.actorOf(Props[WorkerActor])

  def receive = {
    case work: String => worker ! work
    case true => println("good job!")
    case _ => println("huh?")
  }
}

class WorkerActor extends Actor {

  val manager = context.system.actorOf(Props[ManagerActor])

  def receive = {
    case work: String => {
      println("done work [" + work + "]")
      manager ! true
    }
    case _ => println("huh?")
  }
}

object Temp extends App {

  val tt = Props[ManagerActor]
  val sys = ActorSystem("lmind")
  val a = sys.actorOf(Props[ManagerActor])
  val b = sys.actorOf(Props[WorkerActor])

  a ! "build car"
  
  val xml = <http></http>
}

trait CustomerRequestCreator {
  this: Actor =>
  def customerRequest(message: String) = {
    this.postStop()
  }
}

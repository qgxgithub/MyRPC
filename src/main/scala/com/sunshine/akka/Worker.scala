package com.sunshine.akka

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

class Worker(val masterHost: String, val masterPort: Int, val memory: Int, val cores: Int) extends Actor {

  var master: ActorSelection = _
  val workerId=UUID.randomUUID().toString
  val HEADTBEAT_INTERVAL_TIME=10000
  override def preStart(): Unit = {
    //与Master建立连接
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
    //向Master注册
    master ! RegisterWorker(workerId,memory,cores)
  }

  override def receive: Receive = {
    case RegisteredWorker(masterURL) => {//向Master注册成功后 Master反向通知Worker匹配
     //在这里进行心跳机制
      import context.dispatcher
      context.system.scheduler.schedule(0 millis,HEADTBEAT_INTERVAL_TIME millis,self,SendHeartbeat)
    }
    case SendHeartbeat=>{
      println("send heart to Master")
      master!HeartBeat(workerId)
    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory = args(4).toInt
    val cores = args(5).toInt
    val config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin)
    val actorSystem = ActorSystem("WorkerSystem", config)
    val worker = actorSystem.actorOf(Props(new Worker(masterHost, masterPort, memory, cores)))

  }
}

package com.sunshine.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

class Master(val host: String, val port: Int) extends Actor{
  //workerId->WorkerInfo的映射
  var idToWorker=new mutable.HashMap[String,WorkerInfo]()
  var workers=new mutable.HashSet[WorkerInfo]()
  val HEARTBEAT_OUTTIME=15000
  override def preStart(): Unit = {
    import context.dispatcher
    //在这里启动一个定时器 检测挂掉的worker
    context.system.scheduler.schedule(0 millis,HEARTBEAT_OUTTIME millis,self,CheckHeartBeat)
  }

  override def receive: Receive = {
    case RegisterWorker(workerId,memory,cores)=>{//注册逻辑
      if(!idToWorker.contains(workerId)){//判断是否已经注册过了
        val workerInfo=new WorkerInfo(workerId,memory,cores)
        idToWorker(workerId)=workerInfo
        workers+=workerInfo
        sender!RegisteredWorker(s"akka.tcp://MasterSystem@$host:$port/user/Master")
      }
    }
    case HeartBeat(workerId)=>{
      val worker=idToWorker(workerId)
      worker.lastHeartbeatTime=System.currentTimeMillis()
    }
    case CheckHeartBeat=>{
      val toRemote=workers.filter(w=>System.currentTimeMillis()-w.lastHeartbeatTime>HEARTBEAT_OUTTIME)
      for(r<-toRemote){
        idToWorker-=(r.workerId)
        workers-=r
      }
      println(workers.size)
    }
  }

}

object Master {
  def main(args: Array[String]): Unit = {
    val host=args(0)
    val port=args(1).toInt
    val config=ConfigFactory.parseString(
      s"""
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = "$host"
        |akka.remote.netty.tcp.port = "$port"
      """.stripMargin)
    val actorSystem=ActorSystem("MasterSystem",config)
    //创建actor
    val master = actorSystem.actorOf(Props(new Master(host,port)),"Master")
  }
}

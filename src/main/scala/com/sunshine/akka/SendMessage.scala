package com.sunshine.akka

trait SendMessage extends Serializable

//Worker->Master
  //向Master注册
case class RegisterWorker(workerId:String,memory:Int,cores:Int) extends SendMessage
case class HeartBeat(workerId:String)
//Master->Worker
  //向Worker回调注册成功
case class RegisteredWorker(masterURL:String)extends SendMessage

//Worker->Worker
case object SendHeartbeat

//Master->Master
case object CheckHeartBeat
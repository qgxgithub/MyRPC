package com.sunshine.akka

class WorkerInfo(val workerId: String, val memory: Int, val cores: Int) {
  var lastHeartbeatTime:Long= _
}

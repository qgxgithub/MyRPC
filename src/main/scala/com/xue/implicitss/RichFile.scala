package com.xue.implicitss

import java.io.File

import scala.io.Source
import MyPredef._

class RichFile(val f:File) {
  def read()=Source.fromFile(f).mkString
}

object RichFile {
  def main(args: Array[String]): Unit = {
    val contents=new File("C://aa.txt").read()
    println(contents)
  }
}
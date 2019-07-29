package com.xue.implicitss

//它必须传入进去一个隐士转换函数
//class Chooser[T <% Ordered[T]] {//视图界定
//  def choose(first: T, second: T): T = {
//    if(first>second) first else second
//  }
//}

//上下文界定
//它必须传入隐士转换的一个值
class Chooser[T: Ordering] {
  def choose(first: T, second: T): T = {
    val org = implicitly[Ordering[T]]
    if (org.gt(first, second)) first else second
  }
}

object Chooser {
  def main(args: Array[String]): Unit = {
    import MyPredef._
    val c = new Chooser[Girl]()
    val g1 = new Girl("zhangsan", 11)
    val g2 = new Girl("lisi", 12)
    val g = c.choose(g1, g2)
    println(g.name + "=====" + g.faceValue)

  }
}

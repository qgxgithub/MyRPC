package com.xue.implicitss

import java.io.File

/**
  * 定义自己的门面
  */
object MyPredef {
  implicit def fileToRichFile(f:File)=new RichFile(f)


//  implicit def girl2Ordered(g:Girl)=new Ordered[Girl](){
//    override def compare(that: Girl): Int = {
//      g.faceValue-that.faceValue
//    }
//  }
//  或
//  implicit val girl2Ordered=(g:Girl)=>new Ordered[Girl](){
//    override def compare(that: Girl): Int = {
//      g.faceValue-that.faceValue
//    }
//  }




//  implicit val girl2Ordering=new Ordering[Girl](){
//    override def compare(x: Girl, y: Girl): Int = {
//      x.faceValue-y.faceValue
//    }
//  }



  trait girl2Ordering extends Ordering[Girl]{
    override def compare(x: Girl, y: Girl): Int = {
      x.faceValue-y.faceValue
    }
  }
  implicit object Girl extends girl2Ordering
}

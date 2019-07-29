package com.xue.implicitss


/**
  * 注意所有的 隐式值或隐式方法 必须放进object里面
  */
object Contexts{
  implicit val name:String="123"
}

object ImplicDemo {


  def show1()(implicit name:String): Unit ={
    println(name)
  }

  def show2()(implicit age:Int=1): Unit ={
    println(age)
  }

  def main(args: Array[String]): Unit = {
    //show1()  //这样会报错 必须导入隐式转换  could not find implicit value for parameter name: String
    //show2() //这个不会报错 因为有隐私转换默认值 了


    import Contexts._
    show1()("456")  //456

    //import Contexts._     //导入隐式转换
    //show1()               //123


  }
}

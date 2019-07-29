object MyPredef{
//  implicit def girl2Ordered(g:Girl)=new Ordered[Girl]{
//    override def compare(that: Girl): Int = {
//      g.faceValue-that.faceValue
//    }
//  }

  implicit def girl2Ordering=new Ordering[Girl]{
    override def compare(x: Girl, y: Girl): Int = {
      x.faceValue-y.faceValue
    }
  }
}

/**
  * 视图界定 必须传入进去个隐式转换
  * @param ev$1
  * @tparam T
  */
//class Choose[T <% Ordered[T]]{
//  def chooser(first:T,second:T)={
//    if (first>second)
//      first
//    else
//      second
//  }
//}


/**
  * 上下文界定
  * @param ev$1
  * @tparam T
  */
class Choose[T : Ordering]{

  def chooser(first:T,second:T)={
    val old=implicitly[Ordering[T]]
    if (old.gt(first,second))
      first
    else
      second
  }
}


class Girl(val name:String,var faceValue:Int) {

}

object Girl{
  def main(args: Array[String]): Unit = {
    import MyPredef._
    val choose=new Choose[Girl]();
    var g1=new Girl("qiguangxue",22)
    var g2=new Girl("lisi",21)

    var g3=choose.chooser(g1,g2)
    println(g3.name)
  }
}

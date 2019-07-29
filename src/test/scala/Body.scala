class Body(val name:String,var faceValue:Integer) extends Comparable[Body]{
  override def compareTo(o: Body): Int = {
    if (this.faceValue!=o.faceValue){
      this.faceValue-o.faceValue
    }else{
      this.name.compareTo(o.name)
    }

  }
}

object Body {

}


object TestBoy{
  def main(args: Array[String]): Unit = {
    val b1=new Body("qiguangxue",21)
    val b2=new Body("fdssd",20)
    var array= Array[Body](b1, b2)
    var sorted=array.sortBy[Body](b=>b).reverse  //记得反正一下  默认是降序的
    for (a<-sorted){
      println(a.name)
    }
  }
}
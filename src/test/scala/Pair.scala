/**
  * 这里的T必须是Comparable的子类
  * @tparam T
  */
class Pair[T<:Comparable[T]] {

  def bigger(obj1:T,obj2:T)={
    if (obj1.compareTo(obj2)>0)
      obj1
    else
      obj2
  }
}

object Pair{
  def main(args: Array[String]): Unit = {
    val pair = new Pair[String]
    println(pair.bigger("hadoop", "spark"))

  }
}
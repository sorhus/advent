package two

case class Pair(x: String, y: String) {

  def distance(): Int = {
    x.zip(y).count{
      case(i,j) => i != j
    }
  }

  def union(): String = {
    x.zip(y).filter{
      case(i,j) => i == j
    }
      .map(_._1)
      .mkString
  }
}

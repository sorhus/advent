package two

import cats.Show

case class Pair(x: String, y: String) {

  def distance(): Int = {
    x.zip(y).count{
      case(i,j) => i != j
    }
  }
}

object Pair {
  implicit val pairShow: Show[Pair] = new Show[Pair] {
    override def show(t: Pair): String = t.x.zip(t.y).collect{
        case(i, j) if i == j => i
      }
      .mkString
    }
}
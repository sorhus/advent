import cats.implicits._
import fs2.Stream
import fs2._

object Two extends App[String] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, String] = {
    toString(input)
      .mapAccumulate(Stream[Pure, String]()){ (s, next) =>
        (s ++ Stream.emit(next), s.map(e => Pair(e, next)))
      }
      .map(_._2)
      .flatten
      .filter(_.distance() == 1)
      .map(_.union())
  }
}

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
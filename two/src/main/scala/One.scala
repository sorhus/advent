import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
      toString(input)
        .map(_.sorted.toCharArray)
        .map(Stream.emits(_))
        .map{word =>
          word.groupAdjacentBy(identity)
            .map(_._2.size)
            .filter(i => i == 2 || i == 3)
            .toList
        }
        .map(Data(_))
        .reduce(_ + _)
        .map(_.result())
  }
}

case class Data(twos: Int, threes: Int) {
  def +(that: Data): Data = Data(twos + that.twos, threes + that.threes)
  def result(): Int = twos * threes
}

object Data {
  def apply(numbers: List[Int]): Data = {
    new Data(if(numbers.contains(2)) 1 else 0, if(numbers.contains(3)) 1 else 0)
  }
}
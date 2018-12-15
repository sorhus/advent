import fs2.{Pure, Stream}
import cats.implicits._

object One extends App[Int] {

  def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toInt(input)
      .reduce(_ + _)
      .head
  }
}

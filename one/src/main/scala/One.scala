import fs2.{Pure, Stream, text}
import cats.implicits._

object One extends App[Int] {

  def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    input.through(text.utf8Decode)
      .through(text.lines)
      .map(_.toInt)
      .reduce(_ + _)
      .head
  }
}

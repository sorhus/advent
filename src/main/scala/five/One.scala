package five

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] with Shared {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toString(input)
      .flatMap(s => Stream.emits(s.toSeq))
      .fold(List[Char]())(accumulate)
      .map(_.size)
  }
}

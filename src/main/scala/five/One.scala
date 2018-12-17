package five

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toString(input)
      .flatMap(s => Stream.emits(s.toSeq))
      .fold(List[Char]()) {(result, s) =>
        if(result.nonEmpty && result.head.toLower == s.toLower && result.head != s) {
          result.tail
        } else {
          s :: result
        }
      }
      .map(_.size)
  }
}

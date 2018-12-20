package five

import common.App
import cats.implicits._
import five.One.accumulate
import fs2.{Pure, Stream}

object Two extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    val s: Stream[Pure, String] = toString(input)
    val chars: Stream[Pure, Char] = s.map(_.toSet.toSeq).flatMap(Stream.emits)

    chars.flatMap{c =>
      s.flatMap(s => Stream.emits(s.toSeq))
        .filter(_.toLower != c)
        .fold(List[Char]())(accumulate)
        .map(_.size)
    }
    .reduce(_ min _)
  }

}

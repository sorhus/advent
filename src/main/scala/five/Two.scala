package five

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object Two extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    val s: Stream[Pure, String] = toString(input)
    val chars: Stream[Pure, Char] = s.map(_.toSet.toSeq).flatMap(Stream.emits)

    chars.flatMap{c =>
      s.flatMap(s => Stream.emits(s.toSeq))
        .filter(_.toLower != c)
        .fold(List[Char]()) {(result, s) =>
          if(result.nonEmpty && result.head.toLower == s.toLower && result.head != s) {
            result.tail
          } else {
            s :: result
          }
        }
        .map(_.size)
    }
    .reduce((x,y) => if(x < y) x else y)
  }

}

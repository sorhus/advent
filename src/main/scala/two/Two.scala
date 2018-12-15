package two

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

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


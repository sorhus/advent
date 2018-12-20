package two

import common.App
import fs2.{Pure, Stream}

object Two extends App[Pair] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Pair] = {
    toString(input)
      .mapAccumulate(Stream[Pure, String]()){ (s, next) =>
        (s ++ Stream.emit(next), s.map(e => Pair(e, next)))
      }
      .flatMap(_._2)
      .filter(_.distance() == 1)
  }
}


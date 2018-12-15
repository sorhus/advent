package two

import common.App
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




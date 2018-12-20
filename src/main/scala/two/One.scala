package two

import cats.Monoid
import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Data] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Data] = {
      toString(input)
        .map(_.sorted.toCharArray)
        .map(Stream.emits(_))
        .flatMap{word =>
          word.groupAdjacentBy(identity)
            .map(_._2.size)
            .fold(implicitly[Monoid[Data]].empty)(Data.build)
        }
        .reduceSemigroup
  }
}




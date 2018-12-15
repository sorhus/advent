package three

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toString(input)
      .map(Patch.apply)
      .flatMap(p => Stream.emits(p.cover()))
      .fold(Map[Pair, Int]()) { (counts, p) =>
        counts + ((p, counts.getOrElse(p, 0) + 1))
      }
      .flatMap(counts => Stream.emits(counts.toSeq))
      .collect{case(_, count) if count > 1 => 1}
      .reduce(_ + _)
  }
}

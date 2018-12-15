package one

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] {

  def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toInt(input)
      .reduce(_ + _)
      .head
  }
}

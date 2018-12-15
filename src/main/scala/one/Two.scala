package one

import common.App
import cats.implicits._
import fs2.{Pure, Stream}
import one.Frequencies.Frequency

object Two extends App[Frequency] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Frequency] = {

    toInt(input)
      .repeat
      .mapAccumulate(Frequencies()) { (frequencies: Frequencies, frequency: Frequency) =>
        val result = frequencies.add(frequency)
        (result, result.calibratedFrequency)
      }
      .collect{case(_, Some(f)) => f}
      .head
  }
}

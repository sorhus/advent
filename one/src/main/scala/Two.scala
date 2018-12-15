
import Frequencies.Frequency
import fs2.{Pure, Stream}
import cats.implicits._

object Two extends App[Frequency] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Frequency] = {

    toInt(input)
      .repeat
      .mapAccumulate(Frequencies()) { (frequencies: Frequencies, frequency: Frequency) =>
        val result = frequencies.add(frequency)
        (result, result.calibratedFrequency)
      }
      .map(_._2)
      .collect{
        case Some(f) => f
      }
      .head
  }
}

case class Frequencies(history: Set[Frequency] = Set.empty, current: Frequency = 0, calibrated: Boolean = false) {

  def add(value: Frequency): Frequencies = {
    val next = current + value
    val calibrated = history.contains(next)
    Frequencies(history + current, next, calibrated)
  }

  def calibratedFrequency: Option[Frequency] = if(calibrated) Some(current) else None
}

object Frequencies {
  type Frequency = Int
}

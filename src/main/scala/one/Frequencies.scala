package one

import one.Frequencies.Frequency

case class Frequencies(history: Set[Frequency] = Set.empty, current: Frequency = 0) {

  def add(value: Frequency): Frequencies = Frequencies(history + current, current + value)

  def calibratedFrequency: Option[Frequency] = Some(current).filter(history.contains)
}

object Frequencies {
  type Frequency = Int
}
package one

import one.Frequencies.Frequency

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
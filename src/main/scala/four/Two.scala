package four

import cats.kernel.Semigroup
import common.App
import four.Line.Id
import fs2.{Pure, Stream}

object Two extends App[Guard] with Shared {

  implicit val sg: Semigroup[Guard] = Guard.nMostSleep

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Guard] = {
    input.through(toString)
      .through(toLines)
      .mapAccumulate(List[Line]())(accumulate)
      .collect{case(_, Some(list)) => list.reverse}
      .map(Shift.apply)
      .fold(Map.empty[Id, List[Int]])(accumulate)
      .flatMap(map => Stream.emits(map.toSeq))
      .map(Guard.apply)
      .reduceSemigroup
  }
}

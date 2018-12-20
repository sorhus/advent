package four

import cats.kernel.Semigroup
import common.App
import fs2.{Pure, Stream}

object Two extends App[Guard] with Shared {

  implicit val sg: Semigroup[Guard] = Guard.nMostSleep

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Guard] = {
    input.through(toString)
      .through(toLines)
      .mapAccumulate(List[Line]())(accumulate)
      .collect{case(_, Some(list)) => list.reverse}
      .map(Shift.apply)
      .fold(Map.empty[String, List[Int]]) {(guards, shift) =>
        guards + ((shift.id, shift.asleep ::: guards.getOrElse(shift.id, Nil)))
      }
      .flatMap(map => Stream.emits(map.toSeq))
      .map(Guard.apply)
      .reduceSemigroup
  }
}

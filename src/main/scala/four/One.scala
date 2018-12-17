package four

import cats.implicits._
import common.App
import fs2.{Pure, Stream}

object One extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    val sorted = toString(input)
      .map(Line.apply)
      .toList
      .sortBy(_.date)

    Stream.emits(sorted)
      .mapAccumulate(List[Line]()) {case(list, line) =>
        (list, line) match {
          case(Nil, Line(_, Left(id))) => (line :: Nil, None)
          case(_, Line(_, Right(_))) => (line :: list, None)
          case(_, Line(_, Left(_))) => (line :: Nil, Some(list))
        }
      }
      .collect{case(_, Some(list)) => list.reverse}
      .map(Shift.apply)
      .fold(Map.empty[String, List[Int]]) {(guards, shift) =>
        guards + ((shift.id, shift.asleep ::: guards.getOrElse(shift.id, Nil)))
      }
      .flatMap(map => Stream.emits(map.toSeq))
      .map(Guard.apply)
      .reduce((x,y) => if(x.minutesSleep > y.minutesSleep) x else y)
      .map(g => g.id.toInt * g.mostSleep.get)
  }
}

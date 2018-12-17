package four

import common.App
import cats.implicits._
import fs2.{Pure, Stream}

object Two extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    val sorted: List[Line] = toString(input)
      .map(Line.apply)
      .toList
      .sortBy(_.date) :::
      (Line.last() :: Nil)

    Stream.emits(sorted)
      .mapAccumulate(List[Line]()) {
        case(Nil, line @ Line(_, Left(_))) => (line :: Nil, None)
        case(list, line @ Line(_, Right(_))) => (line :: list, None)
        case(list, line @ Line(_, Left(_))) => (line :: Nil, Some(list))
      }
      .collect{case(_, Some(list)) => list.reverse}
      .map(Shift.apply)
      .fold(Map.empty[String, List[Int]]) {(guards, shift) =>
        guards + ((shift.id, shift.asleep ::: guards.getOrElse(shift.id, Nil)))
      }
      .flatMap(map => Stream.emits(map.toSeq))
      .map(Guard.apply)
      .reduce((g1, g2) => (g1.nMostSleep, g2.nMostSleep) match {
        case (Some(a), Some(b)) => if (a > b) g1 else g2
        case (Some(_), None) => g1
        case _ => g2
      })
      .map(g => g.id.toInt * g.mostSleep.get)
  }
}

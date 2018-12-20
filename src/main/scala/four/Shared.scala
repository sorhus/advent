package four

import cats.Semigroup
import fs2.{Pure, Stream}

trait Shared {

  def toLines(input: Stream[Pure, String]): Stream[Pure, Line] = {
    val sorted = input.map(Line.apply)
      .toList
      .sortBy(_.date) :::
      (Line.last() :: Nil)
    Stream.emits(sorted)
  }

  def accumulate: (List[Line], Line) => (List[Line], Option[List[Line]]) = {
    case(Nil, line @ Line(_, Left(_))) => (line :: Nil, None)
    case(list, line @ Line(_, Right(_))) => (line :: list, None)
    case(list, line @ Line(_, Left(_))) => (line :: Nil, Some(list))
  }
}

package four

case class Shift(id: String, asleep: List[Int])

object Shift {

  def apply(data: List[Line]): Shift = {
    val id = data.head.data.left.get
    val asleep = data.tail
      .map(_.date.split(":")(1).toInt)
      .grouped(2)
      .flatMap {
        case falls :: awakes :: Nil => Range(falls, awakes)
        case falls :: Nil => Range(falls, 60)
      }
      .toList
    Shift(id, asleep)
  }
}
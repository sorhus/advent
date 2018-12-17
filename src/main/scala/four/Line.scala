package four

case class Line(date: String, data: Either[String, String])

object Line {

  private val date = """\[(\d+)-(\d+-\d+ \d+:\d+)\]"""
  private val guard = raw"""$date Guard #(\d+) begins shift"""r
  private val line = raw"""$date (falls asleep|wakes up)"""r

  def apply(s: String): Line = {
    s match {
      case guard(_, datetime, id) => Line(datetime, Left(id))
      case line(_, datetime, data) => Line(datetime, Right(data))
    }
  }

  def last(): Line = {
    Line("", Left(""))
  }
}

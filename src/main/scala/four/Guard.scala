package four

case class Guard(id: String, minutesSleep: Int, mostSleep: Option[Int], nMostSleep: Option[Int])

object Guard {
  def apply(data: (String, List[Int])): Guard = {
    val (mostSleep, nMostSleep) = data._2 match {
      case Nil => (None, None)
      case list =>
        val most = list.groupBy(identity).maxBy(_._2.size)
        (Some(most._1), Some(most._2.size))
    }
    Guard(data._1, data._2.size, mostSleep, nMostSleep)
  }

}

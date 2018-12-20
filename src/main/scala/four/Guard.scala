package four

import cats.Show
import cats.kernel.Semigroup

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

  implicit val guardShow: Show[Guard] = new Show[Guard] {
    override def show(t: Guard): String = s"${t.id.toInt * t.mostSleep.get}"
  }

  val mostSleep: Semigroup[Guard] = new Semigroup[Guard] {
    override def combine(x: Guard, y: Guard): Guard = if (x.minutesSleep > y.minutesSleep) x else y
  }

  val nMostSleep: Semigroup[Guard] = new Semigroup[Guard] {
    override def combine(x: Guard, y: Guard): Guard = (x.nMostSleep, y.nMostSleep) match {
      case (Some(a), Some(b)) => if (a > b) x else y
      case (Some(_), None) => x
      case _ => y
    }
  }

}

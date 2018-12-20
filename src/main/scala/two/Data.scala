package two

import cats.{Monoid, Show}

case class Data(twos: Int, threes: Int)

object Data {
  def apply(numbers: List[Int]): Data = {
    new Data(if(numbers.contains(2)) 1 else 0, if(numbers.contains(3)) 1 else 0)
  }

  implicit val dataMonoid: Monoid[Data] = new Monoid[Data] {
    override def empty: Data = Data(0, 0)
    override def combine(x: Data, y: Data): Data = Data(x.twos + y.twos, x.threes + y.threes)
  }

  implicit val dataShow: Show[Data] = new Show[Data] {
    override def show(t: Data): String = s"${t.twos * t.threes}"
  }

  def build(data: Data, n: Int): Data = n match {
    case 2 => data.copy(twos = 1)
    case 3 => data.copy(threes = 1)
    case _ => data
  }
}
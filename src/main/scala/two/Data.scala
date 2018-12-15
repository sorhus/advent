package two

case class Data(twos: Int, threes: Int) {
  def +(that: Data): Data = Data(twos + that.twos, threes + that.threes)
  def result(): Int = twos * threes
}

object Data {
  def apply(numbers: List[Int]): Data = {
    new Data(if(numbers.contains(2)) 1 else 0, if(numbers.contains(3)) 1 else 0)
  }
}
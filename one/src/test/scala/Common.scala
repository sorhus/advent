import fs2.{Pure, Stream}

trait Common {

  def generateInput(list: List[Int]) = Stream.emits[Pure, Int](list)
    .map(_.toString)
    .intersperse("\n")
    .map(_.toCharArray.map(_.toByte))
    .flatMap(Stream.emits(_))

}

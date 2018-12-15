package three

import fs2.{Pure, Stream}

trait Common {

  def generateInput(list: List[String]): Stream[Pure, Byte] = Stream.emits[Pure, String](list)
    .intersperse("\n")
    .map(_.toCharArray.map(_.toByte))
    .flatMap(Stream.emits(_))
}

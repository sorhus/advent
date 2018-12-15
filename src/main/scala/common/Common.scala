package common

import fs2.{Pure, Stream}

trait Common {

  def generateInput[T](list: List[T]): Stream[Pure, Byte] = {
    Stream.emits[Pure, T](list)
      .map(_.toString)
      .intersperse("\n")
      .map(_.toCharArray.map(_.toByte))
      .flatMap(Stream.emits(_))
  }

}

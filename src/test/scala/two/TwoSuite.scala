package two

import common.Common
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TwoSuite extends FlatSpec with Matchers with Common {

  it should "compute result of 12" in {
    val input = generateInput(List("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz"))
    val expected = List(Pair("fghij","fguij"))

    Two.process(input).toList should be(expected)
  }

}









package two

import common.Common
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class OneSuite extends FlatSpec with Matchers with Common {

  it should "compute result of 12" in {
    val input = generateInput(List("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"))
    val expected = List(12)

    One.process(input).toList should be(expected)
  }
}

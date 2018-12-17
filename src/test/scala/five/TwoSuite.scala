package five

import common.Common
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class TwoSuite extends FlatSpec with Matchers with Common {

  it should "compute 4" in {
    val input = generateInput(List("dabAcCaCBAcCcaDA"))
    val expected = List(4)

    Two.process(input).toList should be(expected)
  }
}
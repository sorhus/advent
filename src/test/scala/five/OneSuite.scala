package five

import common.Common
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class OneSuite extends FlatSpec with Matchers with Common {

  it should "compute 10" in {
    val input = generateInput(List("dabAcCaCBAcCcaDA"))
    val expected = List(10)

    One.process(input).toList should be(expected)
  }
}
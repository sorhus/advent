package one

import common.Common
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TwoSuite extends FlatSpec with Matchers with Common {

  it should "compute frequency of 0" in {
    val input = generateInput(List(1,-1))
    val expected = List(0)

    Two.process(input).compile.toList should be(expected)
  }

  it should "compute frequency of 10" in {
    val input = generateInput(List(3, 3, 4, -2, -4))
    val expected = List(10)

    Two.process(input).toList should be(expected)
  }

  it should "compute frequency of 5" in {
    val input = generateInput(List(-6, 3, 8, 5, -6))
    val expected = List(5)

    Two.process(input).toList should be(expected)
  }

  it should "compute frequency of 14" in {
    val input = generateInput(List(7, 7, -2, -7, -4))
    val expected = List(14)

    Two.process(input).toList should be(expected)
  }
}

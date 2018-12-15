import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class OneSuite extends FlatSpec with Matchers with Common {

  it should "compute sum of 3" in {
    val input = generateInput(List(1,1,1))
    val expected = List(3)

    One.process(input).toList should be(expected)
  }

  it should "compute sum of 0" in {
    val input = generateInput(List(1,1,-2))
    val expected = List(0)

    One.process(input).toList should be(expected)
  }

  it should "compute sum of -6" in {
    val input = generateInput(List(-1,-2,-3))
    val expected = List(-6)

    One.process(input).toList should be(expected)
  }
}

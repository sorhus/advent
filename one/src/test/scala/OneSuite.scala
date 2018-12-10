import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import fs2.{Pure, Stream}

@RunWith(classOf[JUnitRunner])
class OneSuite extends FlatSpec with Matchers with Common {

  it should "compute sum of 3" in {
    val input = generateInput(List(1,1,1))
    val expected = Stream.emit[Pure, Int](3).toList

    One.process(input).toList should be(expected)
  }

  it should "compute sum of 0" in {
    val input = generateInput(List(1,1,-2))
    val expected = Stream.emit[Pure, Int](0).toList

    One.process(input).toList should be(expected)
  }

  it should "compute sum of -6" in {
    val input = generateInput(List(-1,-2,-3))
    val expected = Stream.emit[Pure, Int](-6).toList

    One.process(input).toList should be(expected)
  }
}

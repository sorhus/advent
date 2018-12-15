import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TwoSuite extends FlatSpec with Matchers with Common {

  it should "compute result of 3" in {
    val input = generateInput(List("#1 @ 1,3: 4x4","#2 @ 3,1: 4x4","#3 @ 5,5: 2x2"))
    val expected = List("3")

    Two.process(input).toList should be(expected)
  }

}

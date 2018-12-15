import cats.implicits._
import fs2.{Pure, Stream}

object One extends App[Int] {

  override def process(input: Stream[Pure, Byte]): Stream[Pure, Int] = {
    toString(input)
      .map(Patch.apply)
      .flatMap(p => Stream.emits(p.cover()))
      .fold(Map[Pair, Int]()) { (counts, p) =>
        counts + ((p, counts.getOrElse(p, 0) + 1))
      }
      .flatMap(counts => Stream.emits(counts.toSeq))
      .collect{case(_, count) if count > 1 => 1}
      .reduce(_ + _)
  }
}

case class Pair(x: Int, y: Int)

case class Patch(id: String, anchor: Pair, size: Pair) {

  def covered(pairs: List[Pair]): Boolean = {
    val cover = this.cover().toSet
    pairs.count(cover.contains) == cover.size
  }

  def cover(): Seq[Pair] = for {
    x <- Range(anchor.x + 1, anchor.x + 1 + size.x)
    y <- Range(anchor.y + 1, anchor.y + 1 + size.y)
  } yield Pair(x, y)
}

object Patch {
  private val patch = raw"""#(\d+) @ (\d+),(\d+): (\d+)x(\d+)"""r

  def apply(s: String): Patch = {
    s match {
      case patch(id, xAnchor, yAnchor, xSize, ySize) =>
        Patch(id, Pair(xAnchor.toInt, yAnchor.toInt), Pair(xSize.toInt, ySize.toInt))
    }
  }

}

import cats.implicits._
import fs2.{Pure, Stream}

object Two extends App[String] {

  override def process(input: fs2.Stream[Pure, Byte]): fs2.Stream[Pure, String] = {
    val patches: Stream[Pure, Patch] = toString(input)
      .map(Patch.apply)

    val pairs: List[Pair] = patches.flatMap(patch => Stream.emits(CoverPatch(patch)))
      .fold(Map[Pair, Int]()) { (counts, p) =>
        counts + ((p.cover, counts.getOrElse(p.cover, 0) + 1))
      }
      .flatMap(counts => Stream.emits(counts.toSeq))
      .collect{case(p, 1)  => p}
      .toList

    patches.filter(_.covered(pairs))
      .map(_.id)
  }
}

case class CoverPatch(cover: Pair, id: String)

object CoverPatch {
  def apply(patch: Patch): Seq[CoverPatch] = {
    patch.cover().map(c => CoverPatch(c, patch.id))
  }
}
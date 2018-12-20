package three

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
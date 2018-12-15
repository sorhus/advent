package three

case class CoverPatch(cover: Pair, id: String)

object CoverPatch {
  def apply(patch: Patch): Seq[CoverPatch] = {
    patch.cover().map(c => CoverPatch(c, patch.id))
  }
}
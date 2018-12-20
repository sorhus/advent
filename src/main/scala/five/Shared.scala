package five

trait Shared {

  def accumulate(acc: List[Char], c: Char): List[Char] = acc match {
    case head :: tail if head.toLower == c.toLower && head != c => tail
    case _ => c :: acc
  }
}

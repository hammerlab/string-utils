package org.hammerlab.strings

trait TruncatedToString {
  override def toString: String = toString(Int.MaxValue)

  /**
   * Like Scala's List.mkString method, but supports truncation.
   *
   * Return the concatenation of an iterator over strings, separated by separator, truncated to at most maxLength
   * characters. If truncation occurs, the string is terminated with ellipses.
   */
  def toString(maxLength: Int,
               separator: String = ",",
               ellipses: String = "…"): String = {

    val builder = StringBuilder.newBuilder
    var remaining: Int = maxLength

    val separatedPieces =
      stringPieces
        .flatMap(piece ⇒ Iterator(separator, piece))
        .drop(1)
        .buffered

    while (separatedPieces.hasNext && remaining >= 0) {
      val piece = separatedPieces.head.toString
      val len = piece.length

      if (len <= remaining) {
        builder.append(piece)
        separatedPieces.next()
        remaining -= len
      } else {
        remaining = -1
      }
    }

    val result = builder.result

    if (separatedPieces.hasNext)
      result.substring(0, maxLength - ellipses.length) + ellipses
    else
      result
  }

  /**
   * Iterator over string representations of data comprising this object.
   */
  def stringPieces: Iterator[String]
}

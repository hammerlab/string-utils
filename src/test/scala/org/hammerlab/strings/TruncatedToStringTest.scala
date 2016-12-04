package org.hammerlab.strings

import org.scalatest.{ FunSuite, Matchers }

class TruncatedToStringTest
  extends FunSuite
    with Matchers {

  test("numbers") {
    val o = new TruncatedToString {
      override def stringPieces = (1 to 10).iterator.map(_.toString)
    }

    o.toString(10) should be("1,2,3,4,5…")
    o.toString(17) should be("1,2,3,4,5,6,7,8,…")
    o.toString(18) should be("1,2,3,4,5,6,7,8,9…")
    o.toString(19) should be("1,2,3,4,5,6,7,8,9,…")
    o.toString(20) should be("1,2,3,4,5,6,7,8,9,10")
    o.toString(100) should be("1,2,3,4,5,6,7,8,9,10")
    o.toString() should be("1,2,3,4,5,6,7,8,9,10")
  }

  test("empties") {
    val o = new TruncatedToString {
      override def stringPieces = Array.fill(5)("").iterator
    }

    o.toString(5) should be(",,,,")
    o.toString(4) should be(",,,,")
    o.toString(3) should be(",,…")
    o.toString(2) should be(",…")
    o.toString(1) should be("…")
  }
}

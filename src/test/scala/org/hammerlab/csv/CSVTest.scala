package org.hammerlab.csv

import org.hammerlab.test.Suite

class CSVTest
  extends Suite {

  // cf. http://stackoverflow.com/questions/30271823/converting-a-case-class-to-csv-in-scala
  case class Person(name: String, age: Int, address: Option[String]) {
    val foo: Int = age * age
  }

  test("people") {
    val ppl =
      Seq(
        Person("alice", 123, None),
        Person("bob", 456, Some("foo"))
      )

    ppl.toCSV().mkString("\n") should be(
      """name,age,address
        |alice,123,
        |bob,456,foo""".stripMargin
    )
  }

  test("no people") {
    Seq[Person]().toCSV().mkString("\n") should be("name,age,address")
  }

  test("no people, no header") {
    Seq[Person]().toCSV(includeHeaderLine = false).mkString("\n") should be("")
  }

  test("non-case-class") {
    implicit val csvRowAble = NCCCSVRowAble

    Seq(
      new NonCaseClass(123),
      new NonCaseClass(456)
    )
    .toCSV()
    .mkString("\n") should be(
      """foo,bar,abc
        |123,123123,NCC(123)
        |456,456456,NCC(456)""".stripMargin
    )
  }
}

// Example class demonstrating custom-CSV-conversion for non-case-classes
class NonCaseClass(val a: Int) {
  val b: String = a.toString + a.toString
  override def toString: String = s"NCC($a)"
}

object NCCCSVRowAble extends CSVRowAble[NonCaseClass] {
  // Represent a NonCaseClass as its two public members as well as a third column which is just its .toString(), defined
  // above.
  override def toCSVRow(t: NonCaseClass): Iterator[Any] =
    Iterator(
      t.a,
      t.b,
      t
    )

  override def columnHeadings: Iterator[String] = Iterator("foo", "bar", "abc")
}

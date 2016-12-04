package org.hammerlab.csv

import org.scalatest.{ FunSuite, Matchers }

import ProductsToCSV._

class ProductsToCSVTest
  extends FunSuite
    with Matchers {

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
}

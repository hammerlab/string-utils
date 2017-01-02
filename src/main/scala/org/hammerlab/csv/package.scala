package org.hammerlab

import org.hammerlab.csv.CaseClassUtil.methodNames

import scala.reflect.runtime.universe.TypeTag

package object csv {

  implicit class CSVAble[T: CSVRowAble](elems: Iterator[T]) {
    private val csvRowAble = implicitly[CSVRowAble[T]]

    def toCSV(includeHeaderLine: Boolean = true): Iterator[String] =
      if (includeHeaderLine) {
        val headerLine = csvRowAble.columnHeadings.mkString(",")
        Iterator(headerLine) ++ toCSV(includeHeaderLine = false)
      } else
        elems
          .map(
            csvRowAble
              .toCSVRow(_)
              .mkString(",")
          )
  }

  trait CSVRowAble[T] {
    def toCSVRow(t: T): Iterator[Any]
    def columnHeadings: Iterator[String]
  }

  def getCSVRowAble[T <: Product : TypeTag] =
    new CSVRowAble[T] {
      // cf. http://stackoverflow.com/questions/30271823/converting-a-case-class-to-csv-in-scala
      override def toCSVRow(t: T): Iterator[Any] =
        t
          .productIterator
          .map {
            case Some(value) => value
            case None => ""
            case rest => rest
          }

      override def columnHeadings: Iterator[String] = methodNames[T]
    }

  implicit def toCSV[T: CSVRowAble](elems: Iterable[T]): CSVAble[T] = elems.iterator
  implicit def toCSV[T: CSVRowAble](elems: Array[T]): CSVAble[T] = elems.iterator

  implicit def productsToCSV[T <: Product: TypeTag](products: Iterable[T]): CSVAble[T] = productsToCSV(products.iterator)
  implicit def productsToCSV[T <: Product: TypeTag](products: Array[T]): CSVAble[T] = productsToCSV(products.iterator)
  implicit def productsToCSV[T <: Product : TypeTag](elems: Iterator[T]): CSVAble[T] = {
    implicit val csvRowAble = getCSVRowAble[T]
    new CSVAble(elems)
  }
}

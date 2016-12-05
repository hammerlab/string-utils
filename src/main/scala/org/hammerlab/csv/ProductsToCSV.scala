package org.hammerlab.csv

import org.hammerlab.csv.CaseClassUtil.methodNames
import org.hammerlab.csv.ProductToCSVRow._

import scala.reflect.runtime.universe.TypeTag

class ProductsToCSV[T <: Product: TypeTag](products: Iterator[T]) {

  def toCSV(includeHeaderLine: Boolean = true): Iterator[String] =
    if (includeHeaderLine) {

      val headerLine = methodNames[T].mkString(",")

      Iterator(headerLine) ++ toCSV(includeHeaderLine = false)
    } else
      products.map(_.toCSV)
}

object ProductsToCSV {
  implicit def toCSV[T <: Product: TypeTag](products: Iterator[T]): ProductsToCSV[T] = new ProductsToCSV(products)
  implicit def toCSV[T <: Product: TypeTag](products: Iterable[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator)
  implicit def toCSV[T <: Product: TypeTag](products: Array[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator)
}

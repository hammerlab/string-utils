package org.hammerlab.pageant.utils

import ProductToCSVRow._

class ProductsToCSV[T <: Product](products: BufferedIterator[T]) {
  def toCSV(includeHeaderLine: Boolean = true): Iterator[String] =
    if (includeHeaderLine) {
      val clazz =
        if (products.hasNext)
          products.head.getClass
        else
          classOf[T]

      val headerLine = clazz.getDeclaredFields.map(_.getName).mkString(",")

      Iterator(headerLine) ++ toCSV(includeHeaderLine = false)
    } else
      products.map(_.toCSV)
}

object ProductsToCSV {
  implicit def apply[T <: Product](products: Iterable[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator.buffered)
  implicit def apply[T <: Product](products: Array[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator.buffered)
}

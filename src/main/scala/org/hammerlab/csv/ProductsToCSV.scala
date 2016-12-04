package org.hammerlab.csv

import org.hammerlab.csv.ProductToCSVRow._

import scala.reflect.ClassTag

class ProductsToCSV[T <: Product: ClassTag](products: Iterator[T]) {
  def toCSV(includeHeaderLine: Boolean = true): Iterator[String] =
    if (includeHeaderLine) {
      val (clazz, firstOpt) =
        if (products.hasNext) {
          val first = products.next()
          (first.getClass, Some(first))
        } else {
          val ctag = implicitly[reflect.ClassTag[T]]
          (ctag.runtimeClass.asInstanceOf[Class[T]], None)
        }

      val headerLine =
        clazz
          .getDeclaredFields
          .map(_.getName)
          .filterNot(_ == "$outer")  // Field added on case classes nested inside another class.
          .mkString(",")

      Iterator(headerLine) ++
        firstOpt.map(_.toCSV).iterator ++
        toCSV(includeHeaderLine = false)
    } else
      products.map(_.toCSV)
}

object ProductsToCSV {
  implicit def apply[T <: Product: ClassTag](products: Iterator[T]): ProductsToCSV[T] = new ProductsToCSV(products)
  implicit def apply[T <: Product: ClassTag](products: Iterable[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator)
  implicit def apply[T <: Product: ClassTag](products: Array[T]): ProductsToCSV[T] = new ProductsToCSV(products.iterator)
}

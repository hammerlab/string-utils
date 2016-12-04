package org.hammerlab.pageant.utils

class ProductToCSVRow(val prod: Product) extends AnyVal {
  def toCSV = prod.productIterator.map {
    case Some(value) => value
    case None => ""
    case rest => rest
  } mkString(",")

  def headerLine: String = prod.getClass.getDeclaredFields.map(_.getName).mkString(",")
}

object ProductToCSVRow {
  implicit def fromProduct(prod: Product): ProductToCSVRow = new ProductToCSVRow(prod)
}

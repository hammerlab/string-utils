package org.hammerlab.csv

class ProductToCSVRow(val prod: Product) extends AnyVal {
  // cf. http://stackoverflow.com/questions/30271823/converting-a-case-class-to-csv-in-scala
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

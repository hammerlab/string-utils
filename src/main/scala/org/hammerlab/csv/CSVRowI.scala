package org.hammerlab.csv

trait CSVRowI {
  self: Product ⇒
  override def toString: String = ProductToCSVRow.fromProduct(this).toCSV
}

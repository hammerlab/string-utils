package org.hammerlab.csv

import scala.reflect.runtime.universe._

object CaseClassUtil {
  // cf. http://stackoverflow.com/a/16079804/544236
  def methodNames[T <: Product: TypeTag]: Iterator[String] =
    typeOf[T]
      .members
      .sorted
      .iterator
      .collect {
        case m: MethodSymbol if m.isCaseAccessor => m
      }
      .map(_.name.toString)
}

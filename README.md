# strings

[![Build Status](https://travis-ci.org/hammerlab/string-utils.svg?branch=master)](https://travis-ci.org/hammerlab/string-utils)
[![Coverage Status](https://coveralls.io/repos/github/hammerlab/string-utils/badge.svg)](https://coveralls.io/github/hammerlab/string-utils)
[![Maven Central](https://img.shields.io/maven-central/v/org.hammerlab/string-utils_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Cstring-utils)

String/CSV utilities.

#### [`org.hammerlab.strings.TruncatedToString`](src/main/scala/org/hammerlab/strings/TruncatedToString.scala)
Interface for classes that can render themselves as an `Iterator[String]`, exposing a `.toString()` that will be truncated to a maximum number of characters.

#### [`org.hammerlab.csv`](src/main/scala/org/hammerlab/csv):
Convert collections of `case class`es (really `Product`s) to CSV rows:

```scala
scala> import org.hammerlab.csv._
scala> case class Foo(num: Int, str: String)
scala> Seq(Foo(123, "abc"), Foo(456, "xyz")).toCSV()
res5: Iterator[String] = non-empty iterator
scala> Seq(Foo(123, "abc"), Foo(456, "xyz")).toCSV().mkString("\n")
res6: String =
num,str
123,abc
456,xyz
```

See [the tests](src/test/scala/org/hammerlab/csv/CSVTest.scala) for more examples, including how to convert non-case-classes.

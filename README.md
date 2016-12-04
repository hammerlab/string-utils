# strings

[![Build Status](https://travis-ci.org/hammerlab/string-utils.svg?branch=master)](https://travis-ci.org/hammerlab/string-utils)
[![Coverage Status](https://coveralls.io/repos/github/hammerlab/string-utils/badge.svg)](https://coveralls.io/github/hammerlab/string-utils)
[![Maven Central](https://img.shields.io/maven-central/v/org.hammerlab/string-utils_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Cstring-utils)

String/CSV utilities:
- `org.hammerlab.strings.TruncatedToString`: interface for classes that can render themselves as an `Iterator[String]` to display a `.toString()` with a maximum number of characters.
- `org.hammerlab.csv`:
  - convert `case class`es (really `Product`s) to CSV rows.
  - convert collections of `Product`s to a CSV, optionally including a header line.

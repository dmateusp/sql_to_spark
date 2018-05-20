package com.github.dmateusp.sql_to_spark.helpers

import scala.util.matching.Regex

package object regex {
  implicit class AugmentedRegex(r: Regex) {
    def ignoreCase: Regex = s"(?i)$r".r
  }
}

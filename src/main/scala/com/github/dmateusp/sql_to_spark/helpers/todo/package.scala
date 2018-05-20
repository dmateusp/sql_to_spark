package com.github.dmateusp.sql_to_spark.helpers

package object todo {
  case class TODO(msg: String) {
    override def toString: String =
      s"// TODO: $msg"
  }
}

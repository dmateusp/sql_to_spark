package com.github.dmateusp.sql_to_spark.helpers

package object indentation {

  private def genIndentation(level: Int = 1): String =
    List.fill(level * 2)(" ").mkString

  case class Indent(text: String, level: Int = 1) {
    override def toString: String =
      text
        .lines
        .map(s => genIndentation(1) + s)
        .mkString("\n")
  }

  implicit val indentToString : Indent => String =
    _.toString

}

package com.github.dmateusp.sql_to_spark.translators

import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.helpers.indentation.Indent
import com.github.dmateusp.sql_to_spark.helpers.todo.TODO

object DataFrameTranslator extends StatementAstTranslator {

  override def translate(stmt: StatementAst): String =
    stmt.root match {
      case s @ Select(_, _) => translateSelect(s)
    }

  def translateSelect: PartialFunction[Select, String] = {
    case Select(selectElems, From(Table(t))) =>
      "spark" +
      Indent(
        s"""
         |.read
         |${TODO(s"add source for $t")}
         |.select(
         |${Indent(selectElems.map(translateSelectElem).mkString(s",\n"))}
         |)
         """.stripMargin
      )
  }

  def translateSelectElem: PartialFunction[SelectElem, String] = {
    case Column(c, Some(rename)) => s"""col("$c").as("$rename")"""
    case Column(c, _) => s"""col("$c")"""
    case Star => """col("*")"""
  }

}

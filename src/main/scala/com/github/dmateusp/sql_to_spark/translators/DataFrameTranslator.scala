package com.github.dmateusp.sql_to_spark.translators

import com.github.dmateusp.sql_to_spark.asts._

object DataFrameTranslator extends StatementAstTranslator {

  override def translate(stmt: StatementAst): String =
    stmt.root match {
      case s @ Select(_, _) => translateSelect(s)
    }

  def translateSelect: PartialFunction[Select, String] = {
    case Select(selectElems, From(Table(t))) =>
      s"""
         |// DF: $t
         |  .select(
         |    ${selectElems.map(translateSelectElem).mkString(",\n")}
         |  )
         """.stripMargin
  }

  def translateSelectElem: PartialFunction[SelectElem, String] = {
    case Column(c) => s"col($c)"
    case Star => "col(*)"
  }

}

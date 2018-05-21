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
    case Renamed(r, name) => s"""${translateRenameable(r)}.as("$name")"""
    case Star => """col("*")"""
    case r: Renameable => translateRenameable(r)
  }

  def translateRenameable: PartialFunction[Renameable, String] = {
    case Column(name) => s"""col("$name")"""
    case NullTypedLiteral(litType) => s"""typedLit[Option[${translateType(litType)}]](None)"""
    case TypedLiteral(lit, litType) => s"""typedLit[${translateType(litType)}]($lit)"""
  }

  def translateType: PartialFunction[LiteralType, String] = {
    case VarChar => "String"
    case Timestamp => "java.sql.Timestamp"
    case BigInt => "java.math.BigInt"
  }

}

package com.github.dmateusp.sql_to_spark

package object asts {

  case class StatementAst(root: StatementRoot)

  sealed trait StatementRoot

  case class Select(columns: List[SelectElem], from: From) extends StatementRoot with FromElem

  sealed trait SelectElem
  case class Column(name: String, as: Option[String] = None) extends SelectElem
  case object Star extends SelectElem

  case class From(from: FromElem)
  sealed trait FromElem
  case class Table(name: String) extends FromElem

}

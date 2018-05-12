package com.github.dmateusp.sql_to_spark

package object asts {

  case class StatementAst(elems: List[StatementElem])

  sealed trait StatementElem

  case class Select(columns: List[SelectElem]) extends StatementElem with FromElem

  sealed trait SelectElem
  case class Column(name: String) extends SelectElem
  case object Star extends SelectElem

  case class From(from: FromElem) extends StatementElem

  sealed trait FromElem
  case class Table(name: String) extends FromElem

}

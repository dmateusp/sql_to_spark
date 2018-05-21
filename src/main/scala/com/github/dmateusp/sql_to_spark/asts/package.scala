package com.github.dmateusp.sql_to_spark

package object asts {

  case class StatementAst(root: StatementRoot)

  sealed trait StatementRoot

  case class Select(columns: List[SelectElem], from: From) extends StatementRoot with FromElem

  sealed trait SelectElem
  case object Star extends SelectElem
  case class NoMatch(text: String) extends SelectElem

  sealed trait Renameable
  case class Renamed(col: Renameable, name: String) extends SelectElem
  case class Column(name: String) extends SelectElem with Renameable

  sealed trait Literal extends SelectElem with Renameable
  case class TypedLiteral(lit: String, litType: LiteralType) extends Literal
  case class NullTypedLiteral(literalType: LiteralType) extends Literal

  sealed trait LiteralType
  case object VarChar extends LiteralType
  case object BigInt extends LiteralType
  case object Timestamp extends LiteralType

  case class From(from: FromElem)
  sealed trait FromElem
  case class Table(name: String) extends FromElem

}

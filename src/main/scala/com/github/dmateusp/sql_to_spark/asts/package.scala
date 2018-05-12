package com.github.dmateusp.sql_to_spark

package object asts {
  sealed trait SQLAst
  case class From(from: FromElement)

  sealed trait FromElement extends SQLAst
  case class Select(columns: List[Column]) extends FromElement

  sealed trait Column extends SQLAst
  case class Name(name: String) extends Column
  case object Star extends Column
}

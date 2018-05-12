package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.tokens._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

package object parsers {
  object SQLParser extends Parsers {
    override type Elem = SQLToken

    def columns: Parser[List[Column]] = {
      val star = STAR ^^ (_ => List(Star))
      val columnName: Parser[Name] = accept("column name", { case NAME(name) => Name(name) })
      star | columnName.+
    }

    def selectStatement: Parser[Select] =
      (SELECT ~ columns) ^^ { case _ ~ columns => Select(columns) }
  }

  class SQLReader(tokens: Seq[SQLToken]) extends Reader[SQLToken] {
    override def first: SQLToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[SQLToken] = new SQLReader(tokens.tail)
  }
}

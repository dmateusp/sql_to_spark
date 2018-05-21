package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.errors.ParserError
import com.github.dmateusp.sql_to_spark.tokens._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

package object parsers {

  object SQLParser extends Parsers {

    override type Elem = SQLToken

    def convertLitType: PartialFunction[LIT_TYPE, LiteralType] = {
      case VARCHAR => VarChar
      case TIMESTAMP => Timestamp
      case BIGINT => BigInt
    }
    def columns: Parser[List[SelectElem]] = {
      val star = STAR ^^^ List(Star)
      val columnName: Parser[Column] = accept("column name", { case NAME(name) => Column(name) })
      val typedLit: Parser[Literal] = accept("typed literal", {
        case TYPED_LITERAL(NULL, litType) => NullTypedLiteral(convertLitType(litType))
        case TYPED_LITERAL(LITERAL(lit), litType) => TypedLiteral(lit, convertLitType(litType))
      })
      val columnRename: Parser[Renamed] = ((columnName | typedLit) ~ AS ~ columnName) ^^ { case column ~ _ ~ Column(rename) => Renamed(column, rename) }
      val noMatch: Parser[NoMatch] = accept("column no match", { case NO_MATCH(text) => NoMatch(text) })

      star | (columnRename | columnName | typedLit | noMatch).+
    }

    def from: Parser[From] = {
      val tableName: Parser[Table] = accept("table name", { case NAME(name) => Table(name) })
      (FROM ~ tableName) ^^ { case _ ~ t => From(t)}
    }

    def select: Parser[Select] =
      (SELECT ~ columns ~ from) ^^ { case _ ~ cols ~ f => Select(cols, f) }

    def statement: Parser[StatementAst] =
      phrase(select ^^ { s => StatementAst(s) })

    def apply(tokens: Iterable[SQLToken]): Either[ParserError, StatementAst] = {
      val reader = new SQLReader(tokens)
      statement(reader) match {
        case NoSuccess(msg, _) => Left(ParserError(msg))
        case Success(result, _) => Right(result)
      }
    }
  }

  class SQLReader(tokens: Iterable[SQLToken]) extends Reader[SQLToken] {
    override def first: SQLToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[SQLToken] = new SQLReader(tokens.tail)
  }

}

package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.tokens._
import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.parsers._

import org.scalatest.{FlatSpec, Matchers}

class SQLParserTest extends FlatSpec with Matchers {
  "select * statement" should "be parsed" in {
    val input: List[SQLToken] = List(SELECT, STAR, FROM, NAME("dw.temp"))
    val expected: StatementAst = StatementAst(
        Select(
          List(Star),
          From(
            Table("dw.temp")
          )
        )
    )

    Right(expected) shouldBe SQLParser(input)
  }

  "select 'column name' statement" should "be parsed" in {
    val input: List[SQLToken] = List(SELECT, NAME("user_id"), NAME("uuid"), FROM, NAME("dw.temp"))
    val expected: StatementAst = StatementAst(
      Select(
        List(Column("user_id"), Column("uuid")),
        From(
          Table("dw.temp")
        )
      )
    )

    Right(expected) shouldBe SQLParser(input)
  }

  "select 'column rename' statement" should "be parsed" in {
    val input: List[SQLToken] = List(SELECT, NAME("user_id"), AS, NAME("id"), FROM, NAME("dw.temp"))
    val expected: StatementAst = StatementAst(
      Select(
        List(Renamed(Column("user_id"), "id")),
        From(
          Table("dw.temp")
        )
      )
    )

    Right(expected) shouldBe SQLParser(input)
  }

  "select 'column rename' statement followed" should "be parsed" in {
    val input: List[SQLToken] = List(SELECT, NAME("user_id"), AS, NAME("id"), FROM, NAME("dw.temp"))
    val expected: StatementAst = StatementAst(
      Select(
        List(Renamed(Column("user_id"), "id")),
        From(
          Table("dw.temp")
        )
      )
    )

    Right(expected) shouldBe SQLParser(input)
  }
}

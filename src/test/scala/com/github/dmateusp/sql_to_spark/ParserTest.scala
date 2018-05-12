package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.tokens._
import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.parsers._

import org.scalatest.{FlatSpec, Matchers}

class ParserTest extends FlatSpec with Matchers {
  "select * statement" should "be parsed" in {
    val input: List[SQLToken] = List(SELECT, STAR, FROM, NAME("dw.temp"))
    val expected: StatementAst = StatementAst(
      List(
        Select(List(Star)),
        From(Table("dw.temp"))
      )
    )

    Right(expected) shouldBe SQLParser(input)
  }
}

package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.lexers._
import com.github.dmateusp.sql_to_spark.tokens._

import org.scalatest.{FlatSpec, Matchers}

class LexerTest extends FlatSpec with Matchers {

  "simple select * statement" should "generate correct tokens" in {
    val input =
      """
        |select * from dw.table;
      """.stripMargin

    val output = SQLLexer.parse(SQLLexer.tokens, input).get

    val expected = List[SQLToken](SELECT, STAR, FROM, NAME("dw.table"))

    expected shouldBe output
  }

  "select with column names" should "generate correct tokens" in {
    val input =
      """
        |select
        |  dw_batch_id,
        |  ad_id
        |  from dw.table;
      """.stripMargin

    val output = SQLLexer.parse(SQLLexer.tokens, input).get

    val expected = List[SQLToken](SELECT, NAME("dw_batch_id"), NAME("ad_id"), FROM, NAME("dw.table"))

    expected shouldBe output
  }

  "select with column renames" should "generate correct tokens" in {
    val input =
      """
        |select
        |  dw_batch_id as id_batch,
        |  ad_id
        |  from dw.table;
      """.stripMargin

    val output = SQLLexer.parse(SQLLexer.tokens, input).get

    val expected = List[SQLToken](SELECT, NAME("dw_batch_id"), AS, NAME("id_batch"), NAME("ad_id"), FROM, NAME("dw.table"))

    expected shouldBe output
  }
}

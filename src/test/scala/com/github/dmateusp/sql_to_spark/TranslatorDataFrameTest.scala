package com.github.dmateusp.sql_to_spark

import com.github.dmateusp.sql_to_spark.asts._
import com.github.dmateusp.sql_to_spark.translators.DataFrameTranslator
import org.scalatest.{FlatSpec, Matchers}

class TranslatorDataFrameTest extends FlatSpec with Matchers {
  "select * statement" should "translate" in {
    val input = StatementAst(
      Select(
        List(Star),
        From(
          Table("dw.temp")
        )
      )
    )

    val output = DataFrameTranslator.translate(input)

    val expected =
      s"""
         |// DF: dw.temp
         |  .select(
         |    col(*)
         |  )
       """.stripMargin

    expected.trim shouldBe output.trim
  }
}

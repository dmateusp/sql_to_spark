package com.github.dmateusp.sql_to_spark.translators

import com.github.dmateusp.sql_to_spark.asts._

trait StatementAstTranslator {
  def translate(stmt: StatementAst): String
}


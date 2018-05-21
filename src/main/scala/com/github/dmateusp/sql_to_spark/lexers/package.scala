package com.github.dmateusp.sql_to_spark

import scala.util.parsing.combinator.RegexParsers
import com.github.dmateusp.sql_to_spark.tokens._
import com.github.dmateusp.sql_to_spark.helpers.regex.AugmentedRegex

package object lexers {

  object SQLLexer extends RegexParsers {

    override val whiteSpace = """[\s\n;,]+""".r

    def select: Parser[SELECT.type] = "select".r.ignoreCase ^^^ SELECT
    def name: Parser[NAME] = """[a-z_]+\.?[a-z_]*""".r.ignoreCase ^^ (name => NAME(name.stripSuffix(",")))
    def star: Parser[STAR.type] = """\*""".r ^^^ STAR
    def from: Parser[FROM.type] = "from".r.ignoreCase ^^^ FROM
    def as: Parser[AS.type] = "as".r.ignoreCase ^^^ AS
    def typedLiteral: Parser[TYPED_LITERAL] = """[a-z0-9\-,\.]+::[a-z]+""".r.ignoreCase ^^ (
        typedLit =>
          typedLit
            .split("::") match {
            case Array(lit, litType) => {
              val lType: LIT_TYPE =
                litType match {
                  case "varchar" => VARCHAR
                  case "bigint" => BIGINT
                  case "timestamp" => TIMESTAMP
                }

              lit match {
                case "null" => TYPED_LITERAL(NULL, lType)
                case other => TYPED_LITERAL(LITERAL(other), lType)
              }
            }
          }
      )
    def tokens: Parser[List[SQLToken]] = rep1(select | star | from | as | typedLiteral | name)
  }

}

package com.github.dmateusp.sql_to_spark

import scala.util.parsing.combinator.RegexParsers
import com.github.dmateusp.sql_to_spark.tokens._
import com.github.dmateusp.sql_to_spark.helpers.regex.AugmentedRegex

package object lexers {

  object SQLLexer extends RegexParsers {

    override val whiteSpace = """[\s\n;]+""".r

    def select: Parser[SELECT.type] = "select".r.ignoreCase ^^ (_ => SELECT)
    def name: Parser[NAME] = """[a-z_]+\.?[a-z_]*,?""".r.ignoreCase ^^ (name => NAME(name.stripSuffix(",")))
    def star: Parser[STAR.type] = "*" ^^ (_ => STAR)
    def from: Parser[FROM.type] = "from".r.ignoreCase ^^ (_ => FROM)
    def as: Parser[AS.type] = "as".r.ignoreCase ^^ (_ => AS)
    def tokens: Parser[List[SQLToken]] = rep1(select | star | from | as | name)
  }

}

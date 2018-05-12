import com.github.dmateusp.sql_to_spark.lexers.SQLLexer
import com.github.dmateusp.sql_to_spark.parsers.SQLParser
import com.github.dmateusp.sql_to_spark.translators.DataFrameTranslator

object Main extends App {

  println(
    DataFrameTranslator.translate(
      SQLParser(
        io.Source.stdin
          .getLines
          .flatMap(l => SQLLexer.parse(SQLLexer.tokens, l).get)
          .toSeq
      ).right.get
    )
  )

}

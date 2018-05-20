import com.github.dmateusp.sql_to_spark.errors.ParserError
import com.github.dmateusp.sql_to_spark.lexers.SQLLexer
import com.github.dmateusp.sql_to_spark.parsers.SQLParser
import com.github.dmateusp.sql_to_spark.translators.DataFrameTranslator
import scala.util.parsing.combinator.Parsers

import SQLLexer._
object Main extends App {
  args match {
    case Array(fileName) =>
      for {
        tokens <- SQLLexer
                    .parseAll(SQLLexer.tokens, io.Source.fromFile(fileName).bufferedReader) match {
                      case Success(result, _) => Right(result)
                      case Failure(msg, _) => Left(msg)
                      case Error(msg, _) => Left(msg)
                    }
        ast <- SQLParser(tokens)
      } yield println(DataFrameTranslator.translate(ast))
    case _ => println("Arguments passed are invalid, please provide the file name as argument")
  }
}

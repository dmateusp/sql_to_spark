import com.github.dmateusp.sql_to_spark.lexers.SQLLexer
import com.github.dmateusp.sql_to_spark.parsers.SQLParser
import com.github.dmateusp.sql_to_spark.translators.DataFrameTranslator
import SQLLexer._

object Main extends App {
  args match {
    case Array(fileName) =>
      val result =
        for {
          tokens <- SQLLexer
                      .parseAll(SQLLexer.tokens, io.Source.fromFile(fileName).reader) match {
                        case Success(r, _) => Right(r)
                        case Failure(msg, _) => Left(msg)
                        case Error(msg, _) => Left(msg)
                      }
          ast <- SQLParser(tokens)
        } yield DataFrameTranslator.translate(ast)

      result match {
        case Right(r) => println(r)
        case Left(e) => println(e)
      }
    case _ => println("Arguments passed are invalid, please provide the file name as argument")
  }
}

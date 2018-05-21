package com.github.dmateusp.sql_to_spark

package object tokens {
  sealed trait SQLToken

  case object SELECT extends SQLToken
  case class NAME(name: String) extends SQLToken
  case object STAR extends SQLToken
  case object FROM extends SQLToken
  case object AS extends SQLToken
  case object COMMA extends SQLToken
  case object SEMICOLON extends SQLToken
  case object EOF extends SQLToken
  case class NO_MATCH(text: String) extends SQLToken
  case class TYPE(name: String) extends SQLToken

  trait LIT extends SQLToken
  case class LITERAL(lit: String) extends LIT
  case object NULL extends LIT
  case class TYPED_LITERAL(lit: LIT, litType: LIT_TYPE) extends SQLToken

  trait LIT_TYPE extends SQLToken
  case object VARCHAR extends LIT_TYPE
  case object BIGINT extends LIT_TYPE
  case object TIMESTAMP extends LIT_TYPE
}

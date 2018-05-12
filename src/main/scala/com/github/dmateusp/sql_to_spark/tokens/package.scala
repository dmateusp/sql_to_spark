package com.github.dmateusp.sql_to_spark

package object tokens {
  sealed trait SQLToken
  case object SELECT extends SQLToken
  case class NAME(name: String) extends SQLToken
  case object STAR extends SQLToken
  case object FROM extends SQLToken
  case object AS extends SQLToken
}

import scala.sys.process._

lazy val projectSettings = Seq(
  organization := "com.github.dmateusp",
  name := "sql_to_spark",
  scalaVersion := "2.12.10",
  version := "git describe --tags --dirty --always".!!.stripPrefix("v").trim
)

lazy val dependencies = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

lazy val root = project.in(file("."))
  .settings(projectSettings)
  .settings(libraryDependencies ++= dependencies)

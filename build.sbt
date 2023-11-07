ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "org.sl"
ThisBuild / organizationName := "stefanlourens.github.com"

lazy val root = (project in file("."))
  .settings(
    name := "scala-sbt",
    libraryDependencies ++= Seq(
      "com.atlassian.commonmark" % "commonmark" % "0.17.0"
    )
  )


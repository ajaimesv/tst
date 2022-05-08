name := "tst"

version := "1.0" 

lazy val `tst` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies ++= Seq( specs2 % Test , guice )

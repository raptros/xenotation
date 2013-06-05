import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "xenotation"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.0"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")


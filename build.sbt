import scala.sys.process._

name := """play-lb-scheduler"""
organization := "com.rminhas"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.2"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayAkkaHttp2Support)

// This will set the name of the zip file under /target/universal
packageName in Universal := name.value

libraryDependencies ++= Seq(
  guice,
  "org.reactivemongo"      %% "reactivemongo"          % "0.20.3",
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
name := """qicaf1"""
organization := "sfeir"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  guice,
  ehcache,
  cacheApi,
  evolutions,
  jdbc,
  "org.pac4j" %% "play-pac4j" % "5.0.0",
  "org.pac4j" % "pac4j-oauth" % "2.2.1",
  "org.postgresql" % "postgresql" % "42.2.2.jre7",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.mockito" % "mockito-core" % "2.7.22" % Test
)

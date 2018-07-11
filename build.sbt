name := """qicaf1"""
organization := "sfeir"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += ehcache
libraryDependencies += cacheApi
libraryDependencies += evolutions
libraryDependencies += jdbc
libraryDependencies += "org.pac4j" %% "play-pac4j" % "5.0.0"
libraryDependencies += "org.pac4j" % "pac4j-oauth" % "2.2.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2.jre7"
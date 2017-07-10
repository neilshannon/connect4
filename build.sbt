import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import AssemblyKeys._
import sbt.Keys.resolvers

name := "connect4"

organization := "com.ntsdev"

version := "0.0.1"

scalaVersion := "2.11.8"

val ScalatraVersion = "2.5.0"

ScalatraPlugin.scalatraSettings

assemblySettings

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.3" % "test",
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-jackson" % "3.3.0",
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

resolvers += Classpaths.typesafeReleases

initialCommands := "import com.ntsdev.connect4._"

enablePlugins(JettyPlugin)

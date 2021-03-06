name := "mongo-demo-scala"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("org.example")

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "1.0.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)


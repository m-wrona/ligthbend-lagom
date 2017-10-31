organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `ligthbend-lagom-sample` = (project in file("."))
  .aggregate(`ligthbend-lagom-sample-api`, `ligthbend-lagom-sample-impl`, `ligthbend-lagom-sample-stream-api`, `ligthbend-lagom-sample-stream-impl`)

lazy val `ligthbend-lagom-sample-api` = (project in file("ligthbend-lagom-sample-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `ligthbend-lagom-sample-impl` = (project in file("ligthbend-lagom-sample-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`ligthbend-lagom-sample-api`)

lazy val `ligthbend-lagom-sample-stream-api` = (project in file("ligthbend-lagom-sample-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `ligthbend-lagom-sample-stream-impl` = (project in file("ligthbend-lagom-sample-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`ligthbend-lagom-sample-stream-api`, `ligthbend-lagom-sample-api`)

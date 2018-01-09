organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

import com.lightbend.lagom.sbt.LagomImport.lagomLogback

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `lagomscala` = (project in file("."))
  .aggregate(`lagomscala-api`, `lagomscala-impl`, `lagomscala-stream-api`, `lagomscala-stream-impl`, `household-api`, `household-impl`, `valueobjects`)

lazy val `valueobjects` = (project in file("valueobjects"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi, lagomLogback
    )
  )

lazy val `household-api` = (project in file("household-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
  .dependsOn(`valueobjects`)

lazy val `household-impl` = (project in file("household-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`household-api`)

lazy val `lagomscala-api` = (project in file("lagomscala-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagomscala-impl` = (project in file("lagomscala-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagomscala-api`)

lazy val `lagomscala-stream-api` = (project in file("lagomscala-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagomscala-stream-impl` = (project in file("lagomscala-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire
    )
  )
  .dependsOn(`lagomscala-stream-api`, `lagomscala-api`)

lagomCassandraEnabled in ThisBuild := false

import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / organization := "org.mbari"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.0.0"
ThisBuild / testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

lazy val shared = (project in file("shared"))
  .settings(
    name := "shared",
    libraryDependencies ++= Seq(
      circeCore,
      circeGeneric,
      circeParser,
      jackson,
      jacksonTime,
      junit % "test"
    )
  )

lazy val `msdemo-akka` = (project in file("akka"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      akka,
      akkaHttp,
      akkaStream,
      junit % "test"
    )
  )

lazy val `msdemo-cask` = (project in file("cask"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      cask,
      junit % "test"
    )
  )

lazy val `msdemo-finatra` = (project in file("finatra"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      finatraHttp,
      junit % "test"
    )
  )

lazy val `msdemo-helidon` = (project in file("helidon"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      helidonConfig,
      helidonCors,
      helidonMedia,
      helidonWebserver,
      junit % "test"
    )
  )

lazy val `msdemos-http4s` = (project in file("http4s"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      http4sCirce,
      http4sCore,
      http4sDsl,
      http4sEmberServer,
      junit % "test"
    )
  )

lazy val `msdemo-javalin` = (project in file("javalin"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      junit % "test",
      javalin
    )
  )

lazy val `msdemo-scalatra` = (project in file("scalatra"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      jettyServer,
      jettyServlets,
      jettyWebapp,
      junit % "test",
//      logback,
      scalatra,
      scalatraJson,
      json4sJackson
    )
  )

lazy val `msdemo-sparkjava` = (project in file("sparkjava"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      junit % "test",
      sparkJava
    )
  )

lazy val `msdemo-zhttp` = (project in file("zhttp"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      junit % "test",
      zhttp
    )
  )

import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / organization := "org.mbari"
ThisBuild / version      := "0.1.0-SNAPSHOT"
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


lazy val `msdemo-helidon` = (project in file("helidon"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "msdemo-helidon",
    libraryDependencies ++= Seq(
      helidonConfig,
      helidonMedia,
      helidonWebserver,
      junit % "test"
    )
  )

lazy val `msdemo-javalin` = (project in file("javalin"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    // name := "msdemo-zhttp",
    libraryDependencies ++= Seq(
      junit % "test",
      javalin
    )
  )


lazy val `msdemo-sparkjava` = (project in file("sparkjava"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    // name := "msdemo-zhttp",
    libraryDependencies ++= Seq(
      junit % "test",
      sparkJava
    )
  )

lazy val `msdemo-zhttp` = (project in file("zhttp"))
  .dependsOn(shared)
  .enablePlugins(JavaAppPackaging)
  .settings(
    // name := "msdemo-zhttp",
    libraryDependencies ++= Seq(
      junit % "test",
      zhttp
    )
  )


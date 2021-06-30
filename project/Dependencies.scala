import sbt._

object Dependencies {

  lazy val circeCore = "io.circe" %% "circe-core" % "0.14.1"
  lazy val circeGeneric = "io.circe" %% "circe-generic" % "0.14.1"
  lazy val circeParser = "io.circe" %% "circe-parser" % "0.14.1"
  lazy val helidonWebserver = "io.helidon.webserver" % "helidon-webserver" % "2.3.1"
  lazy val helidonMedia = "io.helidon.media" % "helidon-media-jsonp" % "2.3.1"
  lazy val helidonConfig = "io.helidon.config" % "helidon-config-yaml" % "2.3.1"
  lazy val junit = "com.github.sbt" % "junit-interface" % "0.13.2"
  lazy val sparkJava = "com.sparkjava" % "spark-core" % "2.9.3"
  lazy val zhttp = "io.d11" %% "zhttp" % "1.0.0.0-RC17"

}

import sbt._

object Dependencies {

  lazy val cask = "com.lihaoyi" %% "cask" % "0.7.11"
  lazy val circeCore = "io.circe" %% "circe-core" % "0.14.1"
  lazy val circeGeneric = "io.circe" %% "circe-generic" % "0.14.1"
  lazy val circeParser = "io.circe" %% "circe-parser" % "0.14.1"
  lazy val finatraHttp = ("com.twitter" %% "finatra-http" % "21.2.0").cross(CrossVersion.for3Use2_13)
  lazy val helidonConfig = "io.helidon.config" % "helidon-config-yaml" % "2.3.1"
  lazy val helidonCors = "io.helidon.webserver" % "helidon-webserver-cors" % "2.3.1"
  lazy val helidonMedia = "io.helidon.media" % "helidon-media-jsonb" % "2.3.1"
  lazy val helidonWebserver = "io.helidon.webserver" % "helidon-webserver" % "2.3.1"
  lazy val jackson = "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.3"
  lazy val jacksonTime = "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.12.3"
  lazy val javalin = "io.javalin" % "javalin" % "3.13.8"
  lazy val jettyServer = "org.eclipse.jetty" % "jetty-server" % "9.4.42.v20210604"
  lazy val jettyServlets = "org.eclipse.jetty" % "jetty-servlets" % "9.4.42.v20210604"
  lazy val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "9.4.42.v20210604"
  lazy val junit = "com.github.sbt" % "junit-interface" % "0.13.2"
  lazy val scalatra = ("org.scalatra" %% "scalatra" % "2.7.1").cross(CrossVersion.for3Use2_13)
  lazy val sparkJava = "com.sparkjava" % "spark-core" % "2.9.3"
  lazy val zhttp = "io.d11" %% "zhttp" % "1.0.0.0-RC17"

}

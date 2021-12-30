import sbt._

object Dependencies {

  lazy val akka       = ("com.typesafe.akka" %% "akka-actor-typed" % "2.6.17").cross(CrossVersion.for3Use2_13)
  lazy val akkaHttp   = ("com.typesafe.akka" %% "akka-http"        % "10.2.7").cross(CrossVersion.for3Use2_13)
  lazy val akkaStream = ("com.typesafe.akka" %% "akka-stream"      % "2.6.17").cross(CrossVersion.for3Use2_13)

  lazy val cask = "com.lihaoyi" %% "cask" % "0.8.0"

  private val circeVersion = "0.14.1"
  lazy val circeCore       = "io.circe" %% "circe-core"    % circeVersion
  lazy val circeGeneric    = "io.circe" %% "circe-generic" % circeVersion
  lazy val circeParser     = "io.circe" %% "circe-parser"  % circeVersion

  lazy val finatraHttp = ("com.twitter" %% "finatra-http-server" % "21.10.0").cross(CrossVersion.for3Use2_13)

  private val helidonVersion = "2.4.0"
  lazy val helidonConfig     = "io.helidon.config"    % "helidon-config-yaml"    % helidonVersion
  lazy val helidonCors       = "io.helidon.webserver" % "helidon-webserver-cors" % helidonVersion
  lazy val helidonMedia      = "io.helidon.media"     % "helidon-media-jsonb"    % helidonVersion
  lazy val helidonWebserver  = "io.helidon.webserver" % "helidon-webserver"      % helidonVersion

  private val http4sVersion  = "0.23.6"
  lazy val http4sCirce       = "org.http4s" %% "http4s-circe"        % http4sVersion
  lazy val http4sCore        = "org.http4s" %% "http4s-core"         % http4sVersion
  lazy val http4sDsl         = "org.http4s" %% "http4s-dsl"          % http4sVersion
  lazy val http4sEmberClient = "org.http4s" %% "http4s-ember-client" % http4sVersion
  lazy val http4sEmberServer = "org.http4s" %% "http4s-ember-server" % http4sVersion

  private val jacksonVersion = "2.13.0"
  lazy val jackson           = "com.fasterxml.jackson.core"     % "jackson-databind"        % jacksonVersion
  lazy val jacksonTime       = "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion

  lazy val javalin = "io.javalin" % "javalin" % "4.1.1"

  private val jettyVersion = "9.4.44.v20210927"
  lazy val jettyServer     = "org.eclipse.jetty" % "jetty-server"   % jettyVersion
  lazy val jettyServlets   = "org.eclipse.jetty" % "jetty-servlets" % jettyVersion
  lazy val jettyWebapp     = "org.eclipse.jetty" % "jetty-webapp"   % jettyVersion

  lazy val json4sJackson = "org.json4s" %% "json4s-jackson" % "4.0.3"

  lazy val junit = "com.github.sbt" % "junit-interface" % "0.13.2"

  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.7"

  private val scalatraVersion = "2.8.2"
  lazy val scalatra           = ("org.scalatra" %% "scalatra"      % scalatraVersion).cross(CrossVersion.for3Use2_13)
  lazy val scalatraJson       = ("org.scalatra" %% "scalatra-json" % scalatraVersion).cross(CrossVersion.for3Use2_13)

  lazy val sparkJava = "com.sparkjava" % "spark-core" % "2.9.3"

  lazy val vertxWeb = "io.vertx" % "vertx-web" % "4.2.1"

  lazy val zhttp = "io.d11" %% "zhttp" % "1.0.0.0-RC17"

}

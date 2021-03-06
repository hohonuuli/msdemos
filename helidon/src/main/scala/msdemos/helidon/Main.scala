package msdemos.helidon

import io.helidon.common.LogConfig
import io.helidon.webserver.WebServer
import io.helidon.config.Config
import io.helidon.webserver.Routing
import io.helidon.common.reactive.Single
import io.helidon.media.jsonb.JsonbSupport
import io.helidon.webserver.cors.CorsSupport
import io.helidon.webserver.cors.CrossOriginConfig

/** Helidon
  *
  * Notes:
  *   - Java framework
  *   - Uses JSON-B for serialization. To parse a request body, a case class wouldn't work so I created a java object bu
  *     I think a scala class with BeanProperty annotations would also work. JSON-B serializes case classes annotated
  *     with @BeanProperty's just just fine though. Make sure you convert collections to java versions though. Also
  *     you're class will need a better for any serialized collections it owns that returns a java collection instead of
  *     a scala collection.
  *   - I followed the helidon guide to add CORS support but it causes the scala 3 compiler to throw and error due to
  *     cyclic references.
  */
object Main {

  def main(args: Array[String]): Unit = startServer()

  private def startServer(): Single[WebServer] = {
    //LogConfig.configureRuntime()
    val config = Config.create();

    val server = WebServer
      .builder(createRouting(config))
      .config(config.get("server"))
      .addMediaSupport(JsonbSupport.create())
      .build();

    val webserver = server.start()

    webserver
      .thenAccept(ws => {
        println(s"WEB server is up! http://localhost:${ws.port()}")
        ws.whenShutdown()
          .thenRun(() => println("WEB server is DOWN. Good bye!"))
      })
      .exceptionallyAccept(t => {
        println("Startup failed: " + t.getMessage());
        t.printStackTrace(System.err);
      });

    webserver
  }

  private def createRouting(config: Config): Routing = {

    // TODO: enabling CORS causes build to fail in sbt w/ scala 3 due to cyclic reference
//    val corsSupport = CorsSupport
//      .builder()
//      .addCrossOrigin(
//        CrossOriginConfig
//          .builder()
//          .allowOrigins("*")
//          .allowMethods("GET", "PUT")
//          .build()
//      )
//      .addCrossOrigin(CrossOriginConfig.create())
//      .build();

    Routing
      .builder()
      .register("/media", new MediaService)
      .build()
  }
}

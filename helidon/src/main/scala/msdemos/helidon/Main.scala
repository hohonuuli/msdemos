package msdemos.helidon

import io.helidon.common.LogConfig
import io.helidon.webserver.WebServer
import io.helidon.config.Config
import io.helidon.webserver.Routing
import io.helidon.common.reactive.Single
import io.helidon.media.jsonb.JsonbSupport
import _root_.io.helidon.webserver.cors.CorsSupport
import _root_.io.helidon.webserver.cors.CrossOriginConfig

object Main {

  def main(args: Array[String]): Unit = startServer()

  private def startServer(): Single[WebServer] = {
    //LogConfig.configureRuntime()
    val config = Config.create();

    val server = WebServer.builder(createRouting(config))
      .config(config.get("server"))
      .addMediaSupport(JsonbSupport.create())
      .build();

    val webserver = server.start()

    webserver
      .thenAccept(ws => {
        println(s"WEB server is up! http://localhost:${ws.port()}")
        ws.whenShutdown().thenRun(() => println("WEB server is DOWN. Good bye!"))
      })
      .exceptionallyAccept(t => {
        println("Startup failed: " + t.getMessage());
        t.printStackTrace(System.err);
      });

    webserver
  }

  private def createRouting(config: Config): Routing = {
    val corsSupport = CorsSupport.builder()
      .addCrossOrigin(CrossOriginConfig.builder()
        .allowOrigins("*")
        .allowMethods("GET", "PUT")
        .build())
      .addCrossOrigin(CrossOriginConfig.create())
      .build();

    Routing.builder()
      .register("/media", corsSupport, new MediaService)
      .build()
  }
}

package msdemos.helidon

import io.helidon.common.LogConfig
import io.helidon.webserver.WebServer
import io.helidon.config.Config
import io.helidon.webserver.Routing
import io.helidon.media.jsonp.JsonpSupport
import io.helidon.common.reactive.Single

object Main {

  def main(args: Array[String]): Unit = startServer()

  def startServer(): Single[WebServer] = {
    //LogConfig.configureRuntime()
    val config = Config.create();

    val server = WebServer.builder(createRouting(config))
      .config(config.get("server"))
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

  def createRouting(config: Config): Routing = {
    Routing.builder()
      .register("/media", new MediaService)
      .build()
  }
}

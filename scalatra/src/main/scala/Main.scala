package msdemos.scalatra

import org.eclipse.jetty.server._
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import java.util.ArrayList
import java.{util => ju}

object Main {
  def main(args: Array[String]): Unit = {
    val server: Server = new Server
    server.setStopAtShutdown(true)

    val httpConfig = new HttpConfiguration()
    httpConfig.setSendDateHeader(true)
    httpConfig.setSendServerVersion(false)

    val connector = new NetworkTrafficServerConnector(
      server,
      new HttpConnectionFactory(httpConfig)
    )
    val port = 8080
    connector.setPort(port)
    println(s"Starting Scalatra on port $port")
    connector.setIdleTimeout(90000)
    server.addConnector(connector)

    val webApp = new WebAppContext
    webApp.setContextPath("/")
    webApp.setResourceBase("webapp")
    webApp.setEventListeners(Array(new ScalatraListener))

    server.setHandler(webApp)

    server.start()

  }
}

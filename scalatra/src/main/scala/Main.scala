package msdemos.scalatra

import org.eclipse.jetty.server._
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import java.util.ArrayList
import java.{util => ju}


/**
 * Scalatra 
 * 
 * Notes:
 *  - Meant to run in a servlet container like Tomcat or Jetty
 *  - I'm running it via embedded jetty, so this class is mostly jetty 
 *    boilerplate. This setup requires a web.xml file to be present in the
 *    resources/WEB-INF directory.
 *  - Scalatra initialization code is always put in `ScalatraBootstrap.scala`
 *    which needs to be in the root of your src/scala directory (i.e. not in a package)
 *  - Attempted to use json4s support that's built in to Scalatra. That caused the service to fail. 
 *    No error was reported in the logs. I fell back to avoiding scalatra's built in
 *    JSON support and using circe instead.
 * 
 */
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

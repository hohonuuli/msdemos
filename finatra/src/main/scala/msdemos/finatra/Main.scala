package msdemos.finatra

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{
  CommonFilters,
  LoggingMDCFilter,
  TraceIdMDCFilter
}
import com.twitter.finatra.http.routing.HttpRouter

object Main extends HelloWorldServer

class HelloWorldServer extends HttpServer {

  override val defaultHttpPort: String = ":8080"

  given Manifest[CommonFilters] = manifest[CommonFilters]
  given Manifest[MediaController] = manifest[MediaController]

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[MediaController]
  }
}

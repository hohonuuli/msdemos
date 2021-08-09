package msdemos.finatra

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

/** Finatra
  *
  * Notes:
  *   - Compiles with Scala 3.0.0 using cross compilation, but won't run as there's some sort of dependency on a 2.11
  *     library
  *   - Parsing a path params requires a case class with PathParm annotations, so plain case classes won't work.
  */
object Main extends HelloWorldServer

class HelloWorldServer extends HttpServer {

  override val defaultHttpPort: String = ":8080"

  given Manifest[CommonFilters]   = manifest[CommonFilters]
  given Manifest[MediaController] = manifest[MediaController]

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[MediaController]
  }
}

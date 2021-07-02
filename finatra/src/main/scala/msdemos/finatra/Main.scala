package msdemos.finatra

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

object Main extends HelloWorldServer

class HelloWorldServer extends HttpServer {

  /*
[error] -- Error: /Users/brian/playspace/webdemos/msdemos/finatra/src/main/scala/msdemos/finatra/Main.scala:14:28
[error] 14 |      .filter[CommonFilters]
[error]    |                            ^
[error]    |No Manifest available for com.twitter.finatra.http.filters.CommonFilters.
   */
  override def configureHttp(router: HttpRouter): Unit = {
//    router
//      .filter[CommonFilters]
//      .add[MediaController]
  }
}
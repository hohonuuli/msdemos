package msdemos.scalatra

import org.scalatra.ScalatraServlet
import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder, VideoSequence}
import org.scalatra.FutureSupport
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
//import org.json4s.*
//import org.scalatra.json.*

class MediaApi(executionContext: ExecutionContext) extends ScalatraServlet with FutureSupport {
//object MediaApi extends ScalatraServlet with JacksonJsonSupport {

//  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ customFormats
//  given Manifest[RequestCounts] = manifest[RequestCounts] // needed for json4s

  implicit def executor = executionContext

  after() {
    contentType = "application/json"
  }

  get("/:i/:j/:k") {
    val i           = params.getAs[Int]("i").get
    val j           = params.getAs[Int]("j").get
    val k           = params.getAs[Int]("k").get
    val delayMillis = params.getAs[Long]("delayMillis").getOrElse(0L)
    val rc          = RequestCounts(i, j, k, delayMillis)
    Future(CirceHelper.buildJsonResponse(rc))
  }

  post("/") {
    val body = request.body
    Future(CirceHelper.buildJsonResponse(body))
  }

}

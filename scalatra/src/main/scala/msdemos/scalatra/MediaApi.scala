package msdemos.scalatra

import org.scalatra.ScalatraServlet
import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder, VideoSequence}
//import org.json4s.*
//import org.scalatra.json.*

object MediaApi extends ScalatraServlet {
//object MediaApi extends ScalatraServlet with JacksonJsonSupport {

//  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ customFormats
//  given Manifest[RequestCounts] = manifest[RequestCounts] // needed for json4s

  before() {
    contentType = "application/json"
  }

  get("/:i/:j/:k") {
    val i  = params.getAs[Int]("i").get
    val j  = params.getAs[Int]("j").get
    val k  = params.getAs[Int]("k").get
    val rc = RequestCounts(i, j, k)
    CirceHelper.buildJsonResponse(rc)
  }

  post("/") {
    val body = request.body
    CirceHelper.buildJsonResponse(body)
  }

}

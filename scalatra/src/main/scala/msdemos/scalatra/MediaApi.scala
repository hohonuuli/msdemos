package msdemos.scalatra

import org.scalatra.ScalatraServlet
import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder}

object MediaApi extends ScalatraServlet {

  get("/:i/:j/:k") {
    val i = params.getAs[Int]("i").get
    val j = params.getAs[Int]("j").get
    val k = params.getAs[Int]("k").get
    val rc = RequestCounts(i, j, k)
    CirceHelper.buildJsonResponse(rc)
  }

  post("/") {
    val body = request.body
    CirceHelper.buildJsonResponse(body)
  }
  
}

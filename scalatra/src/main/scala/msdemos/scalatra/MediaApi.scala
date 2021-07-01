package msdemos.scalatra

import org.scalatra.ScalatraServlet
import msdemos.shared.RequestCounts
import msdemos.shared.ResponseBuilder.*

object MediaApi extends ScalatraServlet {

  before() {
    contentType = "application/json"
    response.headers.set("Access-Control-Allow-Origin", "*")
  }

  get("/:i/:j/:k") {
    val i = params.getAs[Int]("i").get
    val j = params.getAs[Int]("j").get
    val k = params.getAs[Int]("k").get
    val rc = RequestCounts(i, j, k)
    responseFromRequestCount(rc)
  }

  post("/") {
    val body = request.body
    responseFromJsonBody(body)
  }
  
}

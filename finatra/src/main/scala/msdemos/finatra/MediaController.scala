package msdemos.finatra

import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.RouteParam
import msdemos.shared.{CirceHelper, RequestCounts}

import scala.reflect.runtime.universe._

class MediaController extends Controller {

  given TypeTag[FRequestCounts] = typeTag[FRequestCounts]
  given TypeTag[RequestCounts]  = typeTag[RequestCounts]
  given TypeTag[String]         = typeTag[String]

  get("/media/demo/:i/:j/:k") { (request: FRequestCounts) =>
    CirceHelper.buildJsonResponse(request.asRequestCount)
  }

  post("/media/demo") { (request: RequestCounts) =>
    CirceHelper.buildJsonResponse(request)
  }
}

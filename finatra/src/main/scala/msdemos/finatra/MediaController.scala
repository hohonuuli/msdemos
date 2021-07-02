package msdemos.finatra

import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.RouteParam
import msdemos.shared.RequestCounts

class MediaController extends Controller {

  /*
[error] -- Error: /Users/brian/playspace/webdemos/msdemos/finatra/src/main/scala/msdemos/finatra/MediaController.scala:11:3
[error] 11 |  }
[error]    |   ^
[error]    |   No TypeTag available for msdemos.finatra.FRequestCounts
[error] -- Error: /Users/brian/playspace/webdemos/msdemos/finatra/src/main/scala/msdemos/finatra/MediaController.scala:15:3
[error] 15 |  }
[error]    |   ^
[error]    |   No TypeTag available for msdemos.shared.RequestCounts

   */
//  get("/media/demo/:i/:j/:k") { (request: FRequestCounts) =>
//    ""
//  }
//
//  post("/media/demo") { (request: RequestCounts) =>
//    ""
//  }
}

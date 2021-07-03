package msdemos.finatra

import com.twitter.finatra.http.annotations.RouteParam
import msdemos.shared.RequestCounts

case class FRequestCounts(
    @RouteParam("i") i: Int,
    @RouteParam("j") j: Int,
    @RouteParam("k") k: Int
) {
  val asRequestCount: RequestCounts = RequestCounts(i, j, k)
}

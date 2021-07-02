package msdemos.finatra

import com.twitter.finatra.http.annotations.RouteParam

case class FRequestCounts(@RouteParam("i") i: Int, @RouteParam("j") j: Int, @RouteParam("k") k: Int)

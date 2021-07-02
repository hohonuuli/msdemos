package msdemos.zhttp


import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder, VideoSequence}
import zhttp.http._
import zhttp.service.Server
import zio._

object Main extends App {

  // -- App DSL
  
  val app = Http.collect {

    case Method.GET        -> Root / "media" / "demo" / i / j / k => 
      val rc = RequestCounts(i.toInt, j.toInt, k.toInt)
      Response.jsonString(CirceHelper.buildJsonResponse(rc))

    case req @ Method.POST -> Root / "media" / "demo" => req.getBodyAsString match {
      case Some(s) => Response.jsonString(CirceHelper.buildJsonResponse(s))
      case None    => Response.status(Status.BAD_REQUEST)
    }
    
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = 
    Server.start(8080, CORS(app)).exitCode


}

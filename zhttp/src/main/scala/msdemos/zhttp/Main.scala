package msdemos.zhttp


import msdemos.shared.RequestCounts
import msdemos.shared.ResponseBuilder.*
import msdemos.shared.VideoSequence
import zhttp.http._
import zhttp.service.Server
import zio._

object Main extends App {

  // -- App DSL
  
  val app = Http.collect {

    case Method.GET        -> Root / "media" / "demo" / i / j / k => 
      val rc = RequestCounts(i.toInt, j.toInt, k.toInt)
      Response.jsonString(responseFromRequestCount(rc))

    case req @ Method.POST -> Root / "media" / "demo" => req.getBodyAsString match {
      case Some(s) => Response.jsonString(responseFromJsonBody(s))
      case None    => Response.status(Status.BAD_REQUEST)
    }
    
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = 
    Server.start(8080, app).exitCode


}

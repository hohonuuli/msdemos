package msdemos.zhttp

import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder, VideoSequence}
import scala.language.postfixOps
import zhttp.endpoint.*
import zhttp.http.*
import zhttp.http.Middleware.cors
import zhttp.service.*
import zhttp.service.server.ServerChannelFactory
import zio.*


/** zio http
  *
  * Notes:
  *   - Generally very simple to get going. The quagmire that is understanding ZIO is lurking though.
  *   - Really fast
  *   - Seems to throw lots of exceptions on requests when it first starts up.
  *   - Note clear on how to handle errors. Exceptions thrown on the server are not propagated to the response/client.
  *     (i.e. no response is sent)
  */
object Main extends App {

  // -- App DSL

  val app = Http.collect {

    case req @ Method.GET -> Root / "media" / "demo" / i / j / k =>
      val readCount = req.url.queryParams
        .get("readCount")
        .map(_.headOption.map(_.toInt))
        .getOrElse(None)
      val rc = RequestCounts(i.toInt, j.toInt, k.toInt, readCount)
      Response.jsonString(CirceHelper.buildJsonResponse(rc))


  } @@ cors(CORS.DefaultCORSConfig) // RC18 handles CORS differently

  // As of RC18, getBodyAsString returns a zio.Task instead of an Option.
  val appM = Http.collectM {
    case req @ Method.POST -> Root / "media" / "demo" =>
      for {
        body <- req.getBodyAsString
      } yield {
        Option(body) match {
          case Some(s) => Response.jsonString(CirceHelper.buildJsonResponse(s))
          case None => Response.status(Status.BAD_REQUEST)
        }
      }
  } @@ cors(CORS.DefaultCORSConfig)

  private val port = 8080
  private val server =
    Server.port(port) ++    // Setup port
      Server.app(app <> appM) // Setup the Http app
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    // Barebones server had 25% error response in testing
    //Server.start(8080, CORS(app)).exitCode
    val nThreads = java.lang.Runtime.getRuntime().availableProcessors()

    server.make
      .use(_ =>
        // Waiting for the server to start *> Ensures the server doesn't die after printing
        console.putStrLn(s"Server started on port $port") *> ZIO.never
      )
      .provideCustomLayer(ServerChannelFactory.auto ++ EventLoopGroup.auto(nThreads))
      .exitCode

  }

}

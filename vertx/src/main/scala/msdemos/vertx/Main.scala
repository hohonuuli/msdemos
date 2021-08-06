package msdemos.vertx

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.core.http.HttpMethod

import scala.jdk.CollectionConverters.*
import io.vertx.ext.web.RoutingContext
import msdemos.shared.Video
import msdemos.shared.VideoSequence
import msdemos.shared.RequestCounts
import msdemos.shared.CirceHelper
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import scala.util.Try
import scala.util.Success
import scala.util.Failure

/**
 * Vertx 
 * 
 * Notes:
 *  - Vertx has JSON support built in, I think it's a thin wrapper around Jackson. I fell
 *    back to using circe to avoid writing my own JSON serializer.
 *  - Docs are pretty good. I ran into a few nits. e.g. don't call ctx.next() if you've used ctx.end())
 *  - Blocking calls need to be made via `blockingHandeler` to move the offiending block
 *    off of the event loop.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx();
    val router = Router.router(vertx);

    // router.route(HttpMethod.GET, "/media/demo/:i/:j/:k")
    router.get("/media/demo/:i/:j/:k")
      .handler(CorsHandler.create) // Add CORS access for "*"
      .blockingHandler(ctx => handleGet(ctx))
      /** Docs say to call ctx.next() in block handler, but it throws an exception */

    router.post("/media/demo")
      .handler(CorsHandler.create) // Add CORS access for "*"
      .handler(BodyHandler.create) // Required to access the body
      .blockingHandler(ctx => handlePost(ctx))

    val server = vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess(s => println(s"Vertx web server started on port ${s.actualPort}"))
  }

  
  /**
   * Handle a GET request. Parses path and query params.
   * @param ctx The vertx routing context
   */
  def handleGet(ctx: RoutingContext): Unit = {

    // get path params
    val i = ctx.pathParam("i").toInt;
    val j = ctx.pathParam("j").toInt;
    val k = ctx.pathParam("k").toInt;

    // get query params
    val delayMillis = ctx.queryParam("delayMillis")
      .asScala
      .map(_.toLong)
      .headOption
      .getOrElse(0L)

    // build response (may block if delayMillis > 0)/. We are not using 
    // vertx's built in JSON handling because I'm lazy.
    val rc = RequestCounts(i, j, k, delayMillis)
    val json = CirceHelper.buildJsonResponse(rc)

    //  Our json object is a string, but we make it look like JSON in 
    // the response.
    ctx.response()
     .putHeader("Content-Type", "application/json")
     .setStatusCode(200)
     .end(json)
  }

  /**
   * Handle a POST request. Parses the body and returns a video sequence. 
   * @param ctx The vertx routing context
   */
  def handlePost(ctx: RoutingContext): Unit = {
    val body = ctx.getBodyAsString()
    val json = CirceHelper.buildJsonResponse(body)
    ctx.response()
      .putHeader("Content-Type", "application/json")
      .setStatusCode(200)
      .end(json)
  }
}
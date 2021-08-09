package msdemos.javalin

import io.javalin.Javalin
import io.javalin.http.Context
import msdemos.shared.{JacksonBuilder, RequestCounts, ResponseBuilder, VideoSequence}
import msdemos.shared.jdk.RequestCounts as JRequestCounts

import scala.jdk.CollectionConverters._
import _root_.io.javalin.core.validation.JavalinValidation

import java.time.Instant
import _root_.io.javalin.plugin.json.JavalinJackson

/**
 * Javalin
 * 
 * Notes: 
 *  - Java framework
 *  - Uses Jackson for object serialization. Works relatively well with case
 *    classes, but be sure all collections return Java collections and 
 *    there's a getter that returns a java collections
 */
object Main {

  def main(args: Array[String]): Unit = {

    val app = Javalin
      .create(config => config.enableCorsForAllOrigins())
      .start(8080)

    JavalinJackson.configure(JacksonBuilder.build())

    app.get("/media/demo/:i/:j/:k", handleGet)
    app.post("/media/demo", handlePost)
  }

  private def handleGet(ctx: Context): Unit = {
    val i           = ctx.pathParam("i").toInt
    val j           = ctx.pathParam("j").toInt
    val k           = ctx.pathParam("k").toInt
    val readCount = ctx.queryParam("readCount", "0").toInt
    val rc          = RequestCounts(i, j, k, Option(readCount))
    ctx.json(VideoSequence.fromBlocking(rc).asJava)
  }

  private def handlePost(ctx: Context): Unit = {
    val rc = ctx.bodyAsClass(classOf[JRequestCounts]).asScala
    ctx.json(VideoSequence.fromBlocking(rc).asJava)
  }

}

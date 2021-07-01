package msdemos.javalin

import io.javalin.Javalin
import io.javalin.http.Context
import msdemos.shared.RequestCounts
import msdemos.shared.jdk.RequestCounts as JRequestCounts
import msdemos.shared.ResponseBuilder.*

import scala.jdk.CollectionConverters._
import msdemos.shared.JacksonBuilder
import _root_.io.javalin.core.validation.JavalinValidation
import java.time.Instant
import _root_.io.javalin.plugin.json.JavalinJackson

object Main {

  def main(args: Array[String]): Unit = {

    val app = Javalin.create().start(8080)

    JavalinJackson.configure(JacksonBuilder.build())

    app.get("/media/demo/:i/:j/:k", handleGet)
    app.post("/media/demo", handlePost)
  }

  private def handleGet(ctx: Context): Unit = {
    val i = ctx.pathParam("i").toInt
    val j = ctx.pathParam("j").toInt
    val k = ctx.pathParam("k").toInt
    val rc = RequestCounts(i, j, k)
    ctx.json(from(rc).asJava)
  }

  private def handlePost(ctx: Context): Unit = {
    val rc = ctx.bodyAsClass(classOf[JRequestCounts]).asScala
    ctx.json(from(rc).asJava)
  }
  

}

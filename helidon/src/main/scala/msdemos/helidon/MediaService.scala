package msdemos.helidon

import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import msdemos.shared.{RequestCounts, VideoSequence}
import msdemos.shared.jdk.RequestCounts as JRequestCounts

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

class MediaService extends Service {

  override def update(rules: Routing.Rules): Unit = {
    rules
      .get("/demo/{i}/{j}/{k}", handleGet)
      .post("/demo", handlePost)
  }

  private def handleGet(req: ServerRequest, res: ServerResponse): Unit = {
    val i = req.path.param("i").toInt
    val j = req.path.param("j").toInt
    val k = req.path.param("k").toInt
    val readCount = req.queryParams
      .first("readCount")
      .toScala
      .map(_.toInt)
    val rc = RequestCounts(i, j, k, readCount)
    res.send(VideoSequence.fromBlocking(rc).asJava)
  }

  private def handlePost(req: ServerRequest, res: ServerResponse): Unit =
    req.content
      .as(classOf[JRequestCounts])
      .thenAccept(body => res.send(VideoSequence.fromBlocking(body.asScala).asJava))

}

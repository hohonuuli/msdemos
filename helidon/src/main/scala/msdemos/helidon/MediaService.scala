package msdemos.helidon

import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import msdemos.shared.RequestCounts
import msdemos.shared.ResponseBuilder.*

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
    val rc = RequestCounts(i, j, k)
    res.send(responseFromRequestCount(rc))
  }

  private def handlePost(req: ServerRequest, res: ServerResponse): Unit =
    req.content
      .as(classOf[String])
      .thenAccept(body => res.send(responseFromJsonBody(body)))
  
  
}

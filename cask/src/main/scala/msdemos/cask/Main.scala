package msdemos.cask

import msdemos.shared.RequestCounts
import msdemos.shared.CirceHelper
import msdemos.shared.VideoSequence
import msdemos.shared.VideoReference
import msdemos.shared.Video
import java.net.URI
import java.util.UUID
import cask.model.Request
import cask.model.Response


/**
 * Cask
 * 
 * Notes:
 *  - Very simple
 *  - No CORS support out of the box
 */
object Main extends cask.MainRoutes {


  @cask.get("/media/demo/:i/:j/:k")
  def handleGet(i: Int, j: Int, k: Int, delayMillis: Long = 0L) = handle(i, j, k, delayMillis)

  @cask.postJson("/media/demo")
  def handlePost(i: Int, j: Int, k: Int, delayMillis: Long) = handle(i, j, k, delayMillis)

  private def handle(i: Int, j: Int, k: Int, delayMillis: Long) = {
    Response(
      CirceHelper.buildJsonResponse(RequestCounts(i, j, k, delayMillis)),
      headers = Seq("Content-Type" -> "application/json",
                    "Access-Control-Allow-Origin" -> "*")
    )
  }

  initialize()

}

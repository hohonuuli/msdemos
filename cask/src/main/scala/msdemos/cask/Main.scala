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
  def handleGet(i: Int, j: Int, k: Int, readCount: Int = 0) = handle(i, j, k, readCount)

  @cask.postJson("/media/demo")
  def handlePost(i: Int, j: Int, k: Int, readCount: Int = 0) = handle(i, j, k, readCount)

  private def handle(i: Int, j: Int, k: Int, readCount: Int) = {
    Response(
      CirceHelper.buildJsonResponse(RequestCounts(i, j, k, Option(readCount))),
      headers = Seq("Content-Type" -> "application/json",
                    "Access-Control-Allow-Origin" -> "*")
    )
  }

  initialize()

}

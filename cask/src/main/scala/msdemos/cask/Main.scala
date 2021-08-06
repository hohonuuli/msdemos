package msdemos.cask

import msdemos.shared.RequestCounts
import msdemos.shared.CirceHelper
import msdemos.shared.VideoSequence
import msdemos.shared.VideoReference
import msdemos.shared.Video
import java.net.URI
import java.util.UUID
import cask.model.Request


/**
 * Cask
 * 
 * Notes:
 *  - Very simple
 *  - As far as I can tell you can either use path params or handle the raw request. 
 *    This is a pain as you need the raw request in order to set the response headers.
 *  - No CORS support out of the box
 */
object Main extends cask.MainRoutes {


  @cask.get("/media/demo/:i/:j/:k")
  def handleGet(i: Int, j: Int, k: Int, delayMillis: Long = 0L) = handle(i, j, k, delayMillis)

  @cask.postJson("/media/demo")
  def handlePost(i: Int, j: Int, k: Int, delayMillis: Long) = handle(i, j, k, delayMillis)

  private def handle(i: Int, j: Int, k: Int, delayMillis: Long) = {
    CirceHelper.buildJsonResponse(RequestCounts(i, j, k, delayMillis))
  }

  initialize()

}

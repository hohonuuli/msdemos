package msdemos.cask

import msdemos.shared.RequestCounts
import msdemos.shared.CirceHelper
import msdemos.shared.VideoSequence
import msdemos.shared.VideoReference
import msdemos.shared.Video
import java.net.URI
import java.util.UUID

object Main extends cask.MainRoutes {

  @cask.get("/media/demo/:i/:j/:k")
  def handleGet(i: Int, j: Int, k: Int) = handle(i, j, k)

  @cask.postJson("/media/demo")
  def handlePost(i: Int, j: Int, k: Int) = handle(i, j, k)

  private def handle(i: Int, j: Int, k: Int) = {
    CirceHelper.buildJsonResponse(RequestCounts(i, j, k))
  }

  initialize()

}

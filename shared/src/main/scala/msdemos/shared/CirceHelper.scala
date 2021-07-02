package msdemos.shared

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.Printer
import CirceCodecs.{given}

object CirceHelper {

  private val printer = Printer.noSpaces

  def buildJsonResponse(body: String): String = {
    decode[RequestCounts](body) match {
      case Left(e) => throw e
      case Right(rc) => buildJsonResponse(rc)
    }
  }

  def buildJsonResponse(rc: RequestCounts): String = {
    val videoSequences = VideoSequence.from(rc)
    val json = videoSequences.asJson
    printer.print(json)
  }

}

package msdemos.shared

import io.circe._
import io.circe.parser._
import io.circe.syntax._
import msdemos.shared.CirceCodecs.{given}
import java.util.UUID
import java.net.URI
import java.time.Instant
import java.time.Duration

object ResponseBuilder {
  
  def from(rc: RequestCounts): Seq[VideoSequence] = {
    for {
      i <- 0 until rc.i
    } yield {

      val v = for {
        j <- 0 until rc.j
      } yield {

        val vr = for {
          k <- 0 until rc.k
        } yield VideoReference(UUID.randomUUID(), URI.create(s"http://myserver.com/$i/$j/$k/somemovie.mp4"))
        Video(UUID.randomUUID, Instant.now, Duration.ofMinutes(i * j), vr)

      }

      VideoSequence(UUID.randomUUID, "Ventana", s"Ventana $i", v)
      
    }
  }

  private val printer = Printer.noSpaces

  def responseFromJsonBody(body: String): String = {
    decode[RequestCounts](body) match {
      case Left(e) => throw e
      case Right(rc) => responseFromRequestCount(rc)
    }
  }

  def responseFromRequestCount(rc: RequestCounts): String = {
    val videoSequences = ResponseBuilder.from(rc)
    val json = videoSequences.asJson
    printer.print(json)
  }

}

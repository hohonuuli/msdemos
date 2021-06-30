package msdemos.shared

import io.circe._
import io.circe.generic.semiauto._
import java.net.URI
import scala.util.Try

object CirceCodecs {

  given Decoder[URI] = Decoder.decodeString.emapTry(s => Try(URI.create(s)))
  given Encoder[URI] = Encoder.encodeString.contramap[URI](_.toString)
  
  given Decoder[VideoReference] = deriveDecoder
  given Encoder[VideoReference] = deriveEncoder

  given Decoder[Video] = deriveDecoder
  given Encoder[Video] = deriveEncoder
  
  given Decoder[VideoSequence] = deriveDecoder
  given Encoder[VideoSequence] = deriveEncoder

  given Decoder[RequestCounts] = deriveDecoder
  given Encoder[RequestCounts] = deriveEncoder
  
}

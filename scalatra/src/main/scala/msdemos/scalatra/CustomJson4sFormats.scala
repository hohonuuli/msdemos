package msdemos.scalatra

import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JNull, JString}

import java.net.URI
import java.time.Instant
import java.util.UUID
import java.time.Duration

given Manifest[UUID]     = manifest[UUID]
given Manifest[URI]      = manifest[URI]
given Manifest[Instant]  = manifest[Instant]
given Manifest[Duration] = manifest[Duration]

def customFormats = UuidSerializer() :: UriSerializer() :: InstantSerializer() :: DurationSerializer() :: Nil

class UuidSerializer
    extends CustomSerializer[UUID](format =>
      (
        {
          case JString(s) => UUID.fromString(s)
          case JNull      => null
        },
        { case x: UUID =>
          JString(x.toString)
        }
      )
    )

class UriSerializer
    extends CustomSerializer[URI](format =>
      (
        {
          case JString(s) => URI.create(s)
          case JNull      => null
        },
        { case x: URI =>
          JString(x.toString)
        }
      )
    )

class InstantSerializer
    extends CustomSerializer[Instant](format =>
      (
        {
          case JString(s) => Instant.parse(s)
          case JNull      => null
        },
        { case x: Instant =>
          JString(x.toString)
        }
      )
    )

class DurationSerializer
    extends CustomSerializer[Duration](format =>
      (
        {
          case JString(s) => Duration.parse(s)
          case JNull      => null
        },
        { case x: Instant =>
          JString(x.toString)
        }
      )
    )

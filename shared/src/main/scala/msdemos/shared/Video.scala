package msdemos.shared

import java.util.UUID
import java.time.Instant
import java.time.Duration

final case class Video(uuid: UUID, startTimestamp: Instant, runtime: Duration, videoReferences: Seq[VideoReference])
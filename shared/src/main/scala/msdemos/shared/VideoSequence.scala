package msdemos.shared

import java.util.UUID

final case class VideoSequence(uuid: UUID, camera: String, deployment: String, videos: Seq[Video])
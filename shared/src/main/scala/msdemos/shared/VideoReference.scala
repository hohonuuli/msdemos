package msdemos.shared

import java.util.UUID
import java.net.URI
import scala.beans.BeanProperty

final case class VideoReference(
    @BeanProperty uuid: UUID,
    @BeanProperty uri: URI
)

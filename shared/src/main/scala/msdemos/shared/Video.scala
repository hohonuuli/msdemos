package msdemos.shared

import java.util.UUID
import java.time.Instant
import java.time.Duration
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

final case class Video(@BeanProperty uuid: UUID, 
  @BeanProperty startTimestamp: Instant, 
  @BeanProperty runtime: Duration, 
  videoReferences: Seq[VideoReference]) {

  def getVideoReferences() = videoReferences.asJava

}
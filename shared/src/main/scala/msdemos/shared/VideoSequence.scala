package msdemos.shared

import java.util.UUID
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

final case class VideoSequence(@BeanProperty uuid: UUID, 
  @BeanProperty camera: String, 
  @BeanProperty deployment: String, 
  videos: Seq[Video]) {

    def getVideos() = videos.asJava
  }
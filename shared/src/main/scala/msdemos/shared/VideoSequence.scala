package msdemos.shared

import java.net.URI
import java.time.{Duration, Instant}
import java.util.UUID
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

final case class VideoSequence(
    @BeanProperty uuid: UUID,
    @BeanProperty camera: String,
    @BeanProperty deployment: String,
    videos: Seq[Video]) {

  def getVideos() = videos.asJava
}

object VideoSequence {

  /**
   * This method simulates a blocking request. It will put the current thread to sleep for the provided duration before returning a value
   * @param rc
   * @param delayMillis
   * @return
   */
  def fromBlocking(rc: RequestCounts, delayMillis: Long = 20L): Seq[VideoSequence] = {

    if (rc.delayMillis > 0L) {
      Thread.sleep(rc.delayMillis)
    }


    for {
      i <- 0 until rc.i
    } yield {
      

      val v = for {
        j <- 0 until rc.j
      } yield {

        val vr = for {
          k <- 0 until rc.k
        } yield VideoReference(
          UUID.randomUUID(),
          URI.create(s"http://myserver.com/$i/$j/$k/somemovie.mp4")
        )
        Video(UUID.randomUUID, Instant.now, Duration.ofMinutes(i * j), vr)

      }

      VideoSequence(UUID.randomUUID, "Ventana", s"Ventana $i", v)

    }
  }
}

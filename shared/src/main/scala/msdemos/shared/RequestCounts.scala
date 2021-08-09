package msdemos.shared

import scala.beans.BeanProperty

final case class RequestCounts(
    @BeanProperty i: Int,
    @BeanProperty j: Int,
    @BeanProperty k: Int,
    readCount: Option[Int] = None
) {
    def getReadCount: Int = readCount.getOrElse(0)
}

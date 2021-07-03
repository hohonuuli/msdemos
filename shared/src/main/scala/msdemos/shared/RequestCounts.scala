package msdemos.shared

import scala.beans.BeanProperty

final case class RequestCounts(
    @BeanProperty i: Int,
    @BeanProperty j: Int,
    @BeanProperty k: Int
)

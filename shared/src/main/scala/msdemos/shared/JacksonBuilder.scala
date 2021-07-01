package msdemos.shared

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.Instant
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.module.SimpleModule

object JacksonBuilder {

  object InstantSerializer extends StdSerializer[Instant](classOf[Instant]) {
    
    override def serialize(value: Instant, jgen: JsonGenerator, provider: SerializerProvider): Unit =
      jgen.writeString(value.toString)

  }

  lazy val customModule = {
    val module = new SimpleModule("MsdemosModule")
    module.addSerializer(classOf[Instant], InstantSerializer)
  }
  
  def build(): ObjectMapper =  ObjectMapper()
    .registerModule(JavaTimeModule())

}

package msdemos.shared

import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import org.junit.Test
import org.junit.Assert.*
import CirceCodecs.{given}

class RequestCountsTest {


  @Test
  def testFromJsonMin(): Unit = {
    val json = """{
      | "i": 1,
      | "j": 2, 
      | "k": 3
      |}
      """.stripMargin
    decode[RequestCounts](json) match {
      case Left(e)   => fail("Failed to parse JSON: " + e.getMessage)
      case Right(rc) => 
        assertEquals(1, rc.i)
        assertEquals(2, rc.j)
        assertEquals(3, rc.k)
        assertTrue(rc.readCount.isEmpty)
    }
  }

  @Test
  def testFromJsonMax(): Unit = {
    val json = """{
      | "i": 1,
      | "j": 2, 
      | "k": 3,
      | "readCount": 4
      |}
      """.stripMargin
    decode[RequestCounts](json) match {
      case Left(e)   => fail("Failed to parse JSON: " + e.getMessage)
      case Right(rc) => 
        assertEquals(1, rc.i)
        assertEquals(2, rc.j)
        assertEquals(3, rc.k)
        assertTrue(rc.readCount.isDefined)
        assertEquals(4, rc.readCount.get)
    }
  }

}
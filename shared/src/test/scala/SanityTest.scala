import org.junit.Test
import org.junit.Assert.*

class SanityTest:
  @Test def t1(): Unit =
    assertEquals(
      "I was compiled by Scala 3. :)",
      "I was compiled by Scala 3. :)"
    )

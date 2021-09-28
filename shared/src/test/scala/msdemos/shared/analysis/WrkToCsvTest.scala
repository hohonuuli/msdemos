package msdemos.shared.analysis

import org.junit.Test
import org.junit.Assert.*
import msdemos.shared.analysis.{given, *}

class WrkToCsvTest {

  @Test
  def testParsing(): Unit = {
    val url = getClass.getResource("/wrk_example_output.txt")
    val path = new java.io.File(url.toURI).toPath
    val wrk = WrkToCsv.parse(path)

    val header = asHeader(wrk)
    assertEquals(header, "src,url,latencyMillis,latencyMillisStd,requestsSec,numberOfRequests,durationSec,dataTransferedMb,transferRateMbSec")


    val csv = transform(wrk)
    assertEquals(csv, "wrk_example_output.txt,http://m3.shore.mbari.org/kb/v1/phylogeny/up/Nanomia,4.29,2.00,1873.41,5816,3.10,19.97,6.43")
    

  }
  
}

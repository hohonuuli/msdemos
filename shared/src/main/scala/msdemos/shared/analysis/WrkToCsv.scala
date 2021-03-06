package msdemos.shared.analysis

import java.nio.file.Path
import java.nio.file.Paths
import java.net.URL
import java.nio.file.Files
import scala.util.Using
import scala.jdk.CollectionConverters.*
import java.util.stream.Collectors
import scala.util.Success
import scala.util.Failure
import scala.jdk.CollectionConverters.*
import scala.util.Try

case class WkrResults(
  src: Path,
  url: URL, 
  latencyMillis: Double, 
  latencyMillisStd: Double,
  requestsSec: Double,
  numberOfRequests: Int,
  durationSec: Double,
  dataTransferedMb: Double,
  transferRateMbSec: Double)

object WrkResults {

  def parse(wrkFile: Path): WkrResults = {
    println(wrkFile)
    val lines = scala.io.Source.fromFile(wrkFile.toFile).getLines.toList

    def splitLine(line: String): Array[String] = {
      line.split("\\s+").map(_.trim).filter(_.nonEmpty)
    }

    val url = new URL(splitLine(lines(0))(4))

    val line3 = splitLine(lines(3))
    println(line3.toList)
    val latency = line3(1).replace("ms", "").toDouble
    val latencyStd = if (line3(2).contains("us")) {
      line3(2).replace("us", "").toDouble / 1000
    }
    else {
     line3(2).replace("ms", "").toDouble
    }

    val line5 = splitLine(lines(5))
    println(line5.toList)
    val numberOfRequests = line5(0).toInt
    val durationSec = line5(3).replace("s,", "").toDouble
    val transferMb = if (line5(4).contains("GB")) {
      line5(4).replace("GB", "").toDouble * 1024
    } 
    else {
      line5(4).replace("MB", "").toDouble
    }

    val requestsSec = splitLine(lines(7))(1).toDouble
    val transferSec = splitLine(lines(8))(1).replace("MB", "").toDouble

    WkrResults(wrkFile, url, latency, latencyStd, requestsSec, numberOfRequests, durationSec, transferMb, transferSec)

  }

}

object WrkToCsv {
  def main(args: Array[String]): Unit = {
    val directory = Paths.get(args(0))
    val substring = args(1)
    val csv = Paths.get(args(2))

    val t = Using(Files.list(directory)) { iter =>
      iter.filter(file => !Files.isDirectory(file) && file.toString.contains(substring))
        .collect(Collectors.toList())
        .asScala
    }

    t match {
      case Success(files) =>
        val wrkFiles = files.map(file => WrkResults.parse(file))
          .toList
        writeAsCsv(csv, wrkFiles)
      case Failure(e) =>
        throw e
    }

  }

  

  def writeAsCsv(target: Path, results: List[WkrResults]): Unit = {

    import msdemos.shared.analysis.{given, *}
    Using(Files.newBufferedWriter(target)) { writer =>
      val csv = transform(results)
      writer.write(csv)
    }
    
  }

}

package msdemos.sparkjava

import spark.Spark.*
import msdemos.shared.{CirceHelper, RequestCounts, ResponseBuilder}

/** Sparkjava
  *
  * Notes:
  *   - Wow. this is really simple. Up and running in a few minutes.
  */
object Main {
  def main(args: Array[String]): Unit = {

    port(8080)

    after((req, res) => res.header("Access-Control-Allow-Origin", "*"))

    get(
      "/media/demo/:i/:j/:k",
      (req, res) => {
        val i         = req.params("i").toInt
        val j         = req.params("j").toInt
        val k         = req.params("k").toInt
        val readCount = req.queryParamOrDefault("readCount", "0").toInt
        val rc        = RequestCounts(i, j, k, Option(readCount))
        res.`type`("application/json")
        CirceHelper.buildJsonResponse(rc)
      }
    )

    post(
      "/media/demo",
      (req, res) => {
        res.`type`("application/json")
        CirceHelper.buildJsonResponse(req.body)
      }
    )
  }
}

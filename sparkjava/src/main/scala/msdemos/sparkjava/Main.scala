package msdemos.sparkjava

import spark.Spark.*
import msdemos.shared.RequestCounts
import msdemos.shared.ResponseBuilder.*

object Main {
  def main(args: Array[String]): Unit = {

    port(8080)

    get("/media/demo/:i/:j/:k", (req, res) => {
      val i = req.params("i").toInt
      val j = req.params("j").toInt
      val k = req.params("k").toInt
      val rc = RequestCounts(i, j, k)
      res.`type`("application/json")
      responseFromRequestCount(rc)
    })

    post("/media/demo", (req, res) => {
      res.`type`("application/json")
      responseFromJsonBody(req.body)
    })
  }
}

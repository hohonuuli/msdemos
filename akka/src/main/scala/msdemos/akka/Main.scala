package msdemos.akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import scala.concurrent.ExecutionContext
import msdemos.shared.CustomExecutors
import msdemos.shared.RequestCounts
import msdemos.shared.VideoSequence
import msdemos.shared.CirceHelper
import akka.actor.typed.DispatcherSelector

object Main {

  def main(args: Array[String]): Unit = {
    given system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "msdemos")
    given ExecutionContext         = system.dispatchers.lookup(DispatcherSelector.blocking)

    val route =
      path("media") {
        path("demo") {
          concat(
            pathPrefix(IntNumber) { i =>
              pathPrefix(IntNumber) { j =>
                pathPrefix(IntNumber) { k =>
                  parameters("delayMillis".optional) { opt =>
                    get {
                      val delayMillis = opt.map(_.toLong).getOrElse(0L)
                      val rc          = RequestCounts(i, j, k, delayMillis)
                      val json        = CirceHelper.buildJsonResponse(rc)
                      val resp        = HttpEntity(ContentTypes.`application/json`, json)
                      complete(resp)
                    }
                  }
                }
              }
            },
            post {
              entity(as[String]) { body =>
                val json = CirceHelper.buildJsonResponse(body)
                val resp = HttpEntity(ContentTypes.`application/json`, json)
                complete(resp)
              }
            }
          )
        }
      }

    val bindingFuture = Http()
      .newServerAt("localhost", 8080)
      .bindFlow(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind())                 // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}

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

import akka.http.scaladsl.server.directives.DebuggingDirectives
import akka.event.Logging

/**
 * Akka HTTP
 * 
 * Notes:
 *  - The routing DSL is a stinky pile of turds.
 *  - No idea how to extract mutltiple path params from a route.
 *  - Currently post works but get doesn't.
 *  - Official docs use deprecated methods for setting up server. Had to look at source code 
 *    to to get custom methods.
 */
object Main {

  def main(args: Array[String]): Unit = {
    given system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "msdemos")
    given ExecutionContext         = system.dispatchers.lookup(DispatcherSelector.blocking)

    val route =
      pathPrefix("media" / "demo") {
        concat(
          get {
            path(IntNumber) { i =>
              path(IntNumber) { j =>
                path(IntNumber) { k =>
                  parameters("readCount".optional) { opt =>
                    val readCount = opt.map(_.toInt)
                    val rc          = RequestCounts(i, j, k, readCount)
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

    // val routeLogged = DebuggingDirectives.logRequestResult("Client ReST", Logging.InfoLevel)(route)


    val bindingFuture = Http()
      .newServerAt("localhost", 8080)
      .bindFlow(route)
      // .bindFlow(routeLogged)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind())                 // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}

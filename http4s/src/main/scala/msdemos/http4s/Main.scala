package msdemos.http4s

import cats.effect.*
import io.circe.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.circe._
import org.http4s.circe.CirceEntityCodec.*
import org.http4s.ember.server.EmberServerBuilder
import scala.concurrent.ExecutionContext
import msdemos.shared.CustomExecutors
import msdemos.shared.RequestCounts
import msdemos.shared.VideoSequence
import msdemos.shared.CirceCodecs.{given}
import com.comcast.ip4s.Port
import org.http4s.server.middleware.CORS


/**
 * http4s
 * 
 * Notes:
 *  - I'm running on ember server and not blaze. Forums say ember is the future,
 *    but the docs don't have any examples using it so I had to dig into it's 
 *    source code.
 *  - parsing out query parameters is a huge pain in the ass. Requiring 
 *    explicit matchers/decoders to be written for every parameter.
 *  - Being functional there's a lot of F[_] stuff. All examples use cats IO as the F.
 *  - I do not love cats IO.
 */
object Main extends IOApp {

  given ExecutionContext = ExecutionContext.fromExecutorService(CustomExecutors.newForkJoinPool())

  object ReadCountQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Int]("readCount")

  val mediaService = HttpRoutes
    .of[IO] {
      case req @ GET -> Root / "media" / "demo" / i / j / k :? ReadCountQueryParamMatcher(readCount) =>
        val rc          = RequestCounts(i.toInt, j.toInt, k.toInt, readCount)
        val media       = VideoSequence.fromBlocking(rc)
        Ok(media)
      case req @ POST -> Root / "media" / "demo" =>
        for {
          rc   <- req.as[RequestCounts]
          resp <- Ok(VideoSequence.fromBlocking(rc))
        } yield resp
    }
    .orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withPort(Port.fromInt(8080).get)
      .withHttpApp(CORS(mediaService))
      .build
      .use(server =>
        IO.delay(println(s"Server Has Started at ${server.address}")) >>
          IO.never.as(ExitCode.Success)
      )

}

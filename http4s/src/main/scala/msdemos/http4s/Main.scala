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

object Main extends IOApp {

  given ExecutionContext = ExecutionContext.fromExecutorService(CustomExecutors.newForkJoinPool())

  object DelayMillisQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Long]("delayMillis")

  val mediaService = HttpRoutes
    .of[IO] {
      case req @ GET -> Root / "media" / "demo" / i / j / k :? DelayMillisQueryParamMatcher(opt) =>
        val delayMillis = opt.getOrElse(0L)
        val rc          = RequestCounts(i.toInt, j.toInt, k.toInt, delayMillis)
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

import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import msdemos.scalatra.MediaApi
import scala.concurrent.ExecutionContext
import msdemos.shared.CustomExecutors

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext): Unit = {

    println("STARTING UP NOW")
    // Optional because * is the default
    context.setInitParameter("org.scalatra.cors.allowedOrigins", "*")
    // Disables cookies, but required because browsers will not allow passing credentials to wildcard domains
    context.setInitParameter("org.scalatra.cors.allowCredentials", "false")
    context.setInitParameter(
      "org.scalatra.cors.allowedMethods",
      "GET, POST, ORIGIN"
    )

    val executionContext = ExecutionContext.fromExecutorService(CustomExecutors.newForkJoinPool())

    context.mount(MediaApi(executionContext), "/media/demo")

  }

}

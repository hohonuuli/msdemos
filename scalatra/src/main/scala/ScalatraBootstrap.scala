import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import msdemos.scalatra.MediaApi

object ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext): Unit = {

    println("STARTING UP NOW")
    context.mount(MediaApi, "/media/demo")

  }
  
}

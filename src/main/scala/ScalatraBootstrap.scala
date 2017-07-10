
import javax.servlet.ServletContext

import com.ntsdev.connect4.web.Connect4API
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new Connect4API, "/*")
  }
}
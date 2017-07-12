package com.ntsdev.connect4.run

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.util.resource.Resource
import java.net.URI

/**
  * Main entry point.  Launches an embedded Jetty Server and bootstraps the API and static content
  */
object JettyLauncher {
  def main(args: Array[String]): Unit = {

    val port = getServerPort
    val context = buildContext

    val server = new Server(port)
    server.setHandler(context)
    server.start()
    server.join()
  }

  private def buildContext = {
    val context = new WebAppContext()
    context.setContextPath("/")
    if(null != System.getenv("VCAP_SERVICES")){
      val webRootLocation = this.getClass.getResource("/webapp/assets/index.html")
      val webRootUri = URI.create(webRootLocation.toURI.toASCIIString.replaceFirst("/assets/index.html$", ""))
      context.setBaseResource(Resource.newResource(webRootUri))
    } else {
      context.setResourceBase("src/main/webapp")
    }
    context.addServlet(classOf[com.ntsdev.connect4.web.Connect4API], "/api/*")
    context.addServlet(classOf[DefaultServlet], "/")
    context.setWelcomeFiles(Array("/assets/index.html"))
    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false")
    context
  }

  private def getServerPort = {
    Option(System.getenv("PORT")).map(_.toInt).getOrElse(8080)
  }

}


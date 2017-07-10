package com.ntsdev.connect4.web;


import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.Grid
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

import scala.concurrent.ExecutionContext


class Connect4API extends ScalatraServlet with JacksonJsonSupport with FutureSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  override val executor: ExecutionContext = ExecutionContext.global

  before() {
    contentType = formats("json")
  }

  get("/startGame"){
    val game = new Game(Grid)
    game.grid
  }
}

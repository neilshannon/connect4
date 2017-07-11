package com.ntsdev.connect4.web;


import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.{Cell, Grid}
import com.ntsdev.connect4.wire.OptionCellSerializer
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

import scala.concurrent.ExecutionContext


class Connect4API extends ScalatraServlet with JacksonJsonSupport with FutureSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats + new OptionCellSerializer

  override val executor: ExecutionContext = ExecutionContext.global

  get("/startGame"){
    contentType = formats("json")
    val game = new Game(Grid)
    game.grid.board
  }

  post("/makeMove"){
    val grid = parsedBody.extract[List[Option[Cell]]]
    val game = new Game(new Grid(grid))
    val column = params("column").toInt
    game.makeMove(column).grid.board
  }
}

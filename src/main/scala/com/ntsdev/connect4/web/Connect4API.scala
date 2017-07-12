package com.ntsdev.connect4.web


import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.{Cell, Board}
import com.ntsdev.connect4.wire.OptionCellSerializer
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

import scala.concurrent.ExecutionContext


class Connect4API extends ScalatraServlet with JacksonJsonSupport with FutureSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats + new OptionCellSerializer

  override val executor: ExecutionContext = ExecutionContext.global

  get("/startGame"){
    val advanced = params("advanced")
    val useAdvancedPlayer = advanced eq "true"
    contentType = formats("json")
    val game = new Game(Board, advanced = useAdvancedPlayer)
    game.grid.board
  }

  post("/makeMove"){
    val grid = parsedBody.extract[List[Option[Cell]]]
    val game = new Game(new Board(grid))
    val column = params("column").toInt
    val newGame = game.makeMove(column)

    if(!"".equals(newGame.winningPlayer)){
      val winningPlayer = newGame.winningPlayer
      Ok(newGame.grid.board, Map("Winning-Player" -> winningPlayer))
    }
    else {
      Ok(newGame.grid.board)
    }
  }
}

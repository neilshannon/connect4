package com.ntsdev.connect4.ai

import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.Board._
import com.ntsdev.connect4.model.RedCell

/**
  * A dumb AI that attempts to play the same column that the player last played, or
  * if not possible on either side.
  */
class SimpleComputerPlayer extends ComputerPlayer {
  def nextMove(game: Game): Int = {
    val grid = game.grid
    //find a red cell and stack on top of it or next to it.
    val lastRedIndex = grid.board.indexWhere({
      case Some(RedCell) => true
      case _ => false
    })

    val index = columnFromIndex(lastRedIndex)
    if(grid.canPlayColumn(index)){
      index
    } else if(grid.canPlayColumn(index + 1)){
      index + 1
    } else if(grid.canPlayColumn(index - 1)) {
      index - 1
    } else {
      -1
    }
  }
}

object SimpleComputerPlayer {
  def apply(): SimpleComputerPlayer = {
    new SimpleComputerPlayer
  }
}

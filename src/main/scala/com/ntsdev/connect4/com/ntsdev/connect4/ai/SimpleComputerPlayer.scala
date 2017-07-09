package com.ntsdev.connect4.com.ntsdev.connect4.ai

import com.ntsdev.connect4.model.{Grid, RedCell}
import com.ntsdev.connect4.model.Grid._

class SimpleComputerPlayer {
  def nextMove(grid: Grid): Int = {
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

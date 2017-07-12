package com.ntsdev.connect4.game

import com.ntsdev.connect4.ai.{AdvancedComputerPlayer, ComputerPlayer, SimpleComputerPlayer}
import com.ntsdev.connect4.model.{BlackCell, Grid, RedCell}

class Game(var grid: Grid, var winningPlayer: String = "", var advanced: Boolean = true) {
  def makeMove(column: Int): Game = {
    val computer = getComputerPlayer(advanced)
    val row = grid.nextCellForColumn(column)
    grid = grid.placeCell(column, row, Some(RedCell))
    if (grid.win) {
      winningPlayer = "RED"
      return new Game(grid, winningPlayer)
    }
    val nextColumn = computer.nextMove(this)
    grid = grid.placeCell(nextColumn, grid.nextCellForColumn(nextColumn), Some(BlackCell))
    if(grid.win){
      winningPlayer = "BLACK"
    }
    if(grid.allCellsFilled){
      grid.win = true
      winningPlayer = "DRAW"
    }
    new Game(grid, winningPlayer, advanced)
  }

  def getAvailableColumns: List[Int] = {
    List.range(0,7).filter(col => grid.canPlayColumn(col))
  }

  def getComputerPlayer(advanced: Boolean): ComputerPlayer = {
    if(advanced){
      AdvancedComputerPlayer()
    } else {
      SimpleComputerPlayer()
    }

  }

}


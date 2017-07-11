package com.ntsdev.connect4.game

import com.ntsdev.connect4.com.ntsdev.connect4.ai.SimpleComputerPlayer
import com.ntsdev.connect4.model.{BlackCell, Grid, RedCell}

class Game(var grid: Grid) {
  def makeMove(column: Int): Game = {
    val computer = SimpleComputerPlayer()
    val row = grid.nextCellForColumn(column)
    grid = grid.placeCell(column, row, Some(RedCell))
    if (grid.win) {
      println("WINNER!!") //TODO send this back to the UI
    }
    val nextColumn = computer.nextMove(grid)
    grid = grid.placeCell(nextColumn, grid.nextCellForColumn(nextColumn), Some(BlackCell))
    if(grid.allCellsFilled){
      println("DRAW!!") //todo send this back to the UI
    }
    new Game(grid)
  }
}


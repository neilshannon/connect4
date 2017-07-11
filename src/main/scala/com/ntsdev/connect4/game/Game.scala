package com.ntsdev.connect4.game

import com.ntsdev.connect4.com.ntsdev.connect4.ai.AdvancedComputerPlayer
import com.ntsdev.connect4.model.{BlackCell, Grid, RedCell}

class Game(var grid: Grid, var winningPlayer: String = "") {
  def makeMove(column: Int): Game = {
    val computer = new AdvancedComputerPlayer()
    val row = grid.nextCellForColumn(column)
    grid = grid.placeCell(column, row, Some(RedCell))
    if (grid.win) {
      winningPlayer = "RED"
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
    new Game(grid, winningPlayer)
  }

  def getAvailableColumns: List[Int] = {
    List.range(0,7).filter(col => grid.canPlayColumn(col))
  }

}


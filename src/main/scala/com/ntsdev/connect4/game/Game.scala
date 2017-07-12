package com.ntsdev.connect4.game

import com.ntsdev.connect4.ai.{AdvancedComputerPlayer, ComputerPlayer, SimpleComputerPlayer}
import com.ntsdev.connect4.model.{BlackCell, Board, RedCell}

/**
  * Models a connect4 game
  * @param grid the board and played pieces
  * @param winningPlayer set if the player wins
  * @param advanced true if the computer player should be advanced
  */
class Game(var grid: Board, var winningPlayer: String = "", var advanced: Boolean = true) {

  /**
    * Make a player move, then the computer will make its move.
    * @param column the column the player should play
    * @return a new Game instance with the pieces played
    */
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

  private def getComputerPlayer(advanced: Boolean): ComputerPlayer = {
    if(advanced){
      AdvancedComputerPlayer()
    } else {
      SimpleComputerPlayer()
    }

  }

}


package com.ntsdev.connect4.game

import com.ntsdev.connect4.model.{BlackCell, Grid}

import scala.io.StdIn

class Game(var grid: Grid) {

  def runGame = {
    //cycle through players, checking for a win on each play.  if no win, check for a draw until all pieces have been filled.
    do {
      println("Choose a column (0-6): ")
      val column = StdIn.readInt()
      val row = grid.nextCellForColumn(column)
      if (row > -1) {
        grid = grid.placeCell(column, row, Some(BlackCell))
      }
      else {
        println("Choose another column.  This column is full")
      }
    } while(!(grid.allCellsFilled || grid.win))
  }



}

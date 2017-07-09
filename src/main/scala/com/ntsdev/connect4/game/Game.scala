package com.ntsdev.connect4.game

import com.ntsdev.connect4.com.ntsdev.connect4.ai.SimpleComputerPlayer
import com.ntsdev.connect4.model.{BlackCell, Grid, RedCell}
import com.ntsdev.connect4.model.Grid._

import scala.io.StdIn

class Game(var grid: Grid) {

  def runGame = {
    import scala.util.control.Breaks._
    val computer = SimpleComputerPlayer()
    //cycle through players, checking for a win on each play.  if no win, check for a draw until all pieces have been filled.
    breakable {
      do {
        print("Choose a column (0-6): ")
        val column = StdIn.readInt()
        val row = grid.nextCellForColumn(column)
        if (row > -1) {
          grid = grid.placeCell(column, row, Some(RedCell))
          drawBoard()
          if (grid.win) {
            break()
          }
          val nextColumn = computer.nextMove(grid)
          grid = grid.placeCell(nextColumn, grid.nextCellForColumn(nextColumn), Some(BlackCell))
          drawBoard()
        }
        else {
          println("Choose another column.  This column is full")
        }
      } while (!(grid.allCellsFilled || grid.win))
    }
  }

  def drawBoard(): Unit = {
    List.range(0, 6).foreach(y => {
      List.range(0, 7).foreach(x => {
        print(grid.cellValue(cellIndex(x, y)).getOrElse("[-]"))
      })
      print('\n')
    })
    print('\n')
  }



}

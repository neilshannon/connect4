package com.ntsdev.connect4.model

class Grid(private val board:List[Option[Cell]]) {

  /*
  Board goes from (0,0) to (6, 5)
   */
  def cellIndex(x: Int, y: Int): Int = {
    (7 * y) + x
  }

  def cellValue(index: Int): Option[Cell] = {
    board(index)
  }

  def placeCell(x: Int, y: Int, cell: Option[Cell]): Grid = {
    val index = cellIndex(x, y)
    board(index) match {
      case None => new Grid(board.updated(index, cell))
      case Some(_) => this
    }
  }

}

/*
  Blank/new game
 */
object Grid extends Grid(board = List.fill(41)(None)) {
  def diagonalLeftToRightIndices(x: Int, y: Int) = {
    List((x, y), (x + 1, y + 1), (x + 2, y + 2), (x + 3, y + 3), (x + 4, y + 4))
  }

  def horizontalIndices(row: Int): List[(Int, Int)] = {
    List.range(0,7).map(x => (x, row))
  }

  def verticalIndices(column: Int): List[(Int, Int)] = {
    List.range(0,6).map(y => (column, y))
  }

  def validIndex(column: Int, row: Int): Boolean = {
    column <= 6 && row <= 5
  }

}


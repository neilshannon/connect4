package com.ntsdev.connect4.model

class Grid(private val board:List[Option[Cell]]) {

  /**
    * Find the flat index based on x, y coordinates. Board goes from (0,0) to (6, 5)
    * @param x row
    * @param y column
    * @return list index
    */
  def cellIndex(x: Int, y: Int): Int = {
    (7 * y) + x
  }

  /**
    * Retrieve the value for a given cell
    * @param index the cell to check
    * @return
    */
  def cellValue(index: Int): Option[Cell] = {
    board(index)
  }

  /**
    * Places a red or black piece in a cell
    * @param x column
    * @param y row
    * @param cell the cell to place
    * @return
    */
  def placeCell(x: Int, y: Int, cell: Option[Cell]): Grid = {
    val index = cellIndex(x, y)
    board(index) match {
      case None => new Grid(board.updated(index, cell))
      case Some(_) => this
    }
  }

  /**
    * Indicates a tie
    * @return true if all cells are filled
    */
  def allCellsFilled: Boolean = {
    board.forall(_.isDefined)
  }

}

/**
  * The game board.  Begins blank.
  */
object Grid extends Grid(board = List.fill(41)(None)) {
  def diagonalBottomRightToTopLeftIndices(x: Int, y: Int): List[(Int, Int)] = {
    List((x, y), (x - 1, y - 1), (x - 2, y - 2), (x - 3, y - 3), (x - 4, y - 4))
  }

  def diagonalTopRightToBottomLeftIndices(x: Int, y: Int): List[(Int, Int)] = {
    List((x, y), (x - 1, y + 1), (x - 2, y + 2), (x - 3, y + 3), (x - 4, y + 4))
  }

  def diagonalBottomLeftToTopRightIndices(x: Int, y: Int): List[(Int, Int)] = {
    List((x, y), (x + 1, y - 1), (x + 2, y - 2), (x + 3, y - 3), (x + 4, y - 4))
  }

  def diagonalTopLeftToBottomRightIndices(x: Int, y: Int): List[(Int, Int)] = {
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


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
    if(index >= 0 && index <= 41)
      board(index)
    else
      None
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

  def winningMove(x: Int, y: Int, cell: Cell): Boolean = {
    val isRed = cell.isInstanceOf[RedCell]
    horizontalWin(x, y, isRed) || verticalWin(x, y, isRed) || diagonalWin(x, y, isRed)
  }

  private def horizontalWin(x: Int, y: Int, isRed: Boolean) = {
    Grid.horizontalIndices(x, y).count(checkCoordinates(_, isRed)) >= 4
  }

  private def verticalWin(x: Int, y: Int, isRed: Boolean) = {
    Grid.verticalIndices(x, y).forall(checkCoordinates(_, isRed))
  }

  private def diagonalWin(x: Int, y: Int, isRed: Boolean) = {
    Grid.diagonalBottomLeftToTopRightIndices(x, y).forall(checkCoordinates(_, isRed)) ||
    Grid.diagonalTopLeftToBottomRightIndices(x, y).forall(checkCoordinates(_, isRed)) ||
    Grid.diagonalBottomRightToTopLeftIndices(x, y).forall(checkCoordinates(_, isRed)) ||
    Grid.diagonalTopRightToBottomLeftIndices(x, y).forall(checkCoordinates(_, isRed))
  }

  private def checkCoordinates(coords: (Int, Int), isRed: Boolean) = {
    cellValue(cellIndex(coords._1, coords._2)) match {
      case Some(cellToCheck) => (isRed && cellToCheck.isInstanceOf[RedCell]) || !isRed && cellToCheck.isInstanceOf[BlackCell]
      case None => false
    }
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

  def horizontalIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x, y), (x + 1, y), (x + 2, y) ,(x + 3, y),
      (x - 1, y), (x - 2, y), (x - 3, y)
    )
  }

  def verticalIndices(column: Int, row: Int): List[(Int, Int)] = {
    List.range(0,6 - row).map(y => (column, y))
  }

  def validIndex(column: Int, row: Int): Boolean = {
    column <= 6 && row <= 5
  }

}


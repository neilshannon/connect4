package com.ntsdev.connect4.model

class Grid(val board:List[Option[Cell]], var win: Boolean = false) {

  def columnFromIndex(index: Int): Int = {
    index match {
      case x if x < 7 => x
      case 0 => 0
      case _ => index % 7
    }
  }

  def rowFromIndex(index: Int): Int = {
    index match {
      case x if x <= 6 => 0
      case y => y / 7
    }
  }


  /**
    * Find the flat index based on x, y coordinates. Board goes from (0,0) to (6, 5)
    *
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
    if(Grid.validIndex(x,y)) {
      board(index) match {
        case None =>
          setCellValue(x, y, cell, index)
        case Some(_) => this
      }
    } else {
      this //no move possible
    }
  }

  private def setCellValue(x: Int, y: Int, cell: Option[Cell], index: Int) = {
    val newGrid = new Grid(board.updated(index, cell))
    if (newGrid.winningMove(x, y, cell.get)) {
      cell match {
        case Some(RedCell) =>
          println("Red Wins!")
          newGrid.win = true
        case Some(BlackCell) =>
          println("Black Wins!")
          newGrid.win = true
        case _ =>
          println("Game Error!")
          System.exit(-1)
      }
    }
    newGrid
  }

  /**
    * Indicates a tie
    *
    * @return true if all cells are filled
    */
  def allCellsFilled: Boolean = {
    board.forall(_.isDefined)
  }

  def winningMove(x: Int, y: Int, cell: Cell): Boolean = {
    val isRed = cell.isInstanceOf[RedCell]
    horizontalWin(x, y, isRed) || verticalWin(x, y, isRed) || diagonalWin(x, y, isRed)
  }

  def canPlayColumn(column: Int): Boolean = {
    nextCellForColumn(column) != -1
  }

  def nextCellForColumn(column: Int): Int = {
    val availableRow = -1
    List.range(5,-1,-1).foreach(row => {
      cellValue(cellIndex(column, row)) match {
        case Some(_) => 0
        case None => return row
      }
    })
    availableRow
  }

  private def horizontalWin(x: Int, y: Int, isRed: Boolean) = {
    val horizontals: List[Option[(Int, Int)]] = Grid.horizontalIndices(x,y).map(Option(_))
    val matchesForPlayer = horizontals.map {
      case Some(coords: (Int, Int)) if Grid.validIndex(coords._1, coords._2) =>
        if (checkCoordinates((coords._1, coords._2), isRed))
          Some(coords)
        else
          None
    }
    val sorted = matchesForPlayer.flatten.sorted
    val (isSequential, count) = checkSequentialColumns(sorted)
    isSequential && count >= 4
  }

  private def verticalWin(x: Int, y: Int, isRed: Boolean) = {
    Grid.downVerticalIndices(x, y).count(checkCoordinates(_, isRed)) >= 4 ||
      Grid.upVerticalIndices(x,y).count(checkCoordinates(_, isRed)) >= 4
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

  private def checkSequentialColumns(listOfCoords: List[(Int, Int)]): (Boolean, Int) = {
    if(listOfCoords.size >= 2) {
      val count = listOfCoords.sliding(2).count(list => list.head._1 + 1 == list(1)._1)
      (count > 0, count + 1)
    }
    else {
      (false, 0)
    }
  }

}

/**
  * The game board.  Begins blank.
  */
object Grid extends Grid(board = List.fill(42)(None), win = false) {

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
      (x - 3, y), (x - 2, y), (x - 1, y), (x, y), (x + 1, y), (x + 2, y) ,(x + 3, y)
    )
  }

  def downVerticalIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x, y), (x, y + 1), (x, y + 2) ,(x, y + 3)
    )
  }

  def upVerticalIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x, y), (x, y - 1), (x, y - 2), (x, y - 3)
    )
  }

  def validIndex(column: Int, row: Int): Boolean = {
    column <= 6 && row <= 5
  }

}



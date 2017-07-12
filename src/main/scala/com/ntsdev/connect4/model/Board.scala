package com.ntsdev.connect4.model
import Board._
import org.slf4j.LoggerFactory

/**
  * The game board
  * @param board
  * @param win
  */
class Board(val board:List[Option[Cell]], var win: Boolean = false) {

  private final val log = LoggerFactory.getLogger(getClass)

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
  def placeCell(x: Int, y: Int, cell: Option[Cell]): Board = {
    val index = cellIndex(x, y)
    if(Board.validIndex(x,y)) {
      board(index) match {
        case None =>
          setCellValue(x, y, cell, index)
        case Some(_) => this
      }
    } else {
      this //no move possible
    }
  }


  /**
    * Indicates a tie
    *
    * @return true if all cells are filled
    */
  def allCellsFilled: Boolean = {
    board.forall(_.isDefined)
  }

  /**
    * Determines if a move has won
    * @param x x index
    * @param y y index
    * @param cell cell to play
    * @return true if the move is a winner
    */
  def winningMove(x: Int, y: Int, cell: Cell): Boolean = {
    val isRed = cell.isInstanceOf[RedCell]
    horizontalWin(x, y, isRed) || verticalWin(x, y, isRed) || diagonalWin(x, y, isRed)
  }

  /**
    * Determines if a column has empty spaces
    * @param column the column to check
    * @return true if the column is playable
    */
  def canPlayColumn(column: Int): Boolean = {
    nextCellForColumn(column) != -1
  }

  /**
    * Finds the next open cell in a given column
    * @param column the column to check
    * @return the next open row in a column
    */
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

  private def setCellValue(x: Int, y: Int, cell: Option[Cell], index: Int) = {
    val newBoard = new Board(board.updated(index, cell))
    cell match {
      case Some(placedCell) =>
        if (newBoard.winningMove(x, y, placedCell)) {
          placedCell match {
            case RedCell =>
              log.debug("Red Wins!")
              newBoard.win = true
            case BlackCell =>
              log.debug("Black Wins!")
              newBoard.win = true
            case _ =>
              log.debug("Game Error!")
              System.exit(-1)
          }
        }
      case _ => //do nothing
    }
    newBoard
  }

  private def horizontalWin(x: Int, y: Int, isRed: Boolean) = {
    val horizontals = Board.horizontalIndices(x,y).map(Option(_))
    checkHorizontalWin(horizontals, isRed)
  }

  private def verticalWin(x: Int, y: Int, isRed: Boolean) = {
   val verticals = Board.verticalIndices(x,y).map(Option(_))
    checkVerticalWin(verticals, isRed)
  }

  private def diagonalWin(x: Int, y: Int, isRed: Boolean) = {
    val bLtoTR = Board.diagonalBottomLeftToTopRightIndices(x, y).map(Option(_))
    val tlToBR = Board.diagonalTopLeftToBottomRightIndices(x, y).map(Option(_))
    checkHorizontalWin(bLtoTR, isRed) || checkHorizontalWin(tlToBR, isRed)
  }

  private def checkHorizontalWin(pieces: List[Option[(Int, Int)]], isRed: Boolean): Boolean = {
    val matchesForPlayer = pieces.map {
      case Some(coords: (Int, Int)) if Board.validIndex(coords._1, coords._2) =>
        if (checkCoordinates((coords._1, coords._2), isRed))
          Some(coords)
        else
          None
      case _ => None
    }
    val sorted = matchesForPlayer.flatten.sorted
    val (isSequential, count) = checkSequentialHorizontalColumns(sorted)
    isSequential && count >= 4
  }

  private def checkVerticalWin(pieces: List[Option[(Int, Int)]], isRed: Boolean): Boolean = {
    val matchesForPlayer = pieces.map {
      case Some(coords: (Int, Int)) if Board.validIndex(coords._1, coords._2) =>
        if (checkCoordinates((coords._1, coords._2), isRed))
          Some(coords)
        else
          None
      case _ => None
    }
    val flat = matchesForPlayer.flatten
    val sorted = flat.sorted
    val (isSequential, count) = checkSequentialVerticalColumns(sorted)
    val win = isSequential && count >= 4
    if(win){
      log.debug("Vertical Win!")
    }
    win
  }


  private def checkCoordinates(coords: (Int, Int), isRed: Boolean) = {
    cellValue(cellIndex(coords._1, coords._2)) match {
      case Some(cellToCheck) => (isRed && cellToCheck.isInstanceOf[RedCell]) || !isRed && cellToCheck.isInstanceOf[BlackCell]
      case None => false
    }
  }

  private def checkSequentialHorizontalColumns(listOfCoords: List[(Int, Int)]): (Boolean, Int) = {
    if(listOfCoords.size >= 2) {
      val count = listOfCoords.sliding(2).count(
        list => list.head._1 + 1 == list(1)._1
      )
      val trueCount = if(listOfCoords.size < 6) count + 1 else count
      (count > 0, trueCount)
    }
    else {
      (false, 0)
    }
  }

  private def checkSequentialVerticalColumns(listOfCoords: List[(Int, Int)]): (Boolean, Int) = {
    if(listOfCoords.size >= 2) {
      val count = listOfCoords.sliding(2).count({ list =>
        list.head._2 + 1 == list(1)._2
      })
      val trueCount = if(listOfCoords.size < 5) count + 1 else count
      (count > 0, trueCount)
    }
    else {
      (false, 0)
    }
  }


}

/**
  * The game board.  Begins blank.
  */
object Board extends Board(board = List.fill(42)(None), win = false) {

  /**
    * Diagonal indices based on an x,y pair
    * @param x x coordinate
    * @param y y coordinate
    * @return a list of diagonals
    */
  def diagonalBottomLeftToTopRightIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x - 5, y + 5), (x - 4, y + 4), (x - 3, y + 3), (x - 2, y + 2), (x - 1, y + 1), (x, y),
      (x + 1, y - 1), (x + 2, y - 2), (x + 3, y - 3),(x + 4, y - 4),(x + 5, y - 5)
    )
  }

  /**
    * Diagonal indices based on an x,y pair
    * @param x x coordinate
    * @param y y coordinate
    * @return a list of diagonals
    */
  def diagonalTopLeftToBottomRightIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
       (x - 5, y - 5),(x - 4, y - 4),(x - 3, y - 3),(x - 2, y - 2),(x - 1, y - 1),(x, y),
       (x + 1, y + 1), (x + 2, y + 2), (x + 3, y + 3),(x + 4, y + 4),(x + 5, y + 5)
    )
  }

  /**
    * Horizontal indices based on an x, y pair
    * @param x x coordinate
    * @param y y coordinate
    * @return a list of coordinates horizontal to the given x,y pair
    */
  def horizontalIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x - 5, y), (x - 4, y), (x - 3, y), (x - 2, y), (x - 1, y), (x, y),
      (x + 1, y), (x + 2, y) ,(x + 3, y),(x + 4, y),(x + 5, y)
    )
  }

  /**
    * Vertical indices to check for a given x, y pair
    * @param x x coordinate
    * @param y y coordinate
    * @return a list of coordinates vertical to the given x,y pair
    */
  def verticalIndices(x: Int, y: Int): List[(Int, Int)] = {
    List(
      (x, y - 5), (x, y - 4), (x, y - 3), (x, y - 2), (x, y - 1), (x, y),
      (x, y + 1), (x, y + 2) ,(x, y + 3), (x, y + 4), (x, y + 5)
    )
  }

  /**
    * Checks if a column and row are on the game board
    * @param column grid column
    * @param row grid row
    * @return true if the column and row are valid
    */
  def validIndex(column: Int, row: Int): Boolean = {
    column <= 6 && column >= 0 && row <= 5 && row >= 0
  }

  /**
    * Find the column from a linear index
    * @param index a linear index
    * @return a column index
    */
  def columnFromIndex(index: Int): Int = {
    index match {
      case x if x < 7 => x
      case 0 => 0
      case _ => index % 7
    }
  }

  /**
    * Find the row from a linear index
    * @param index a linear index
    * @return a row index
    */
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

}



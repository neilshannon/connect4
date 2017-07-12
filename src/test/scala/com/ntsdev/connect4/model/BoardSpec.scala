package com.ntsdev.connect4.model

import org.specs2.mutable.Specification

class BoardSpec extends Specification {

  "The Grid" should {
    "Calculate an index properly" in {
      val testBoard = Board
      testBoard.cellIndex(0, 0) shouldEqual 0 //first column, first row
      testBoard.cellIndex(0, 1) shouldEqual 7 //first column, second row
      testBoard.cellIndex(6, 5) shouldEqual 41 //seventh column, sixth row
    }

    "Check if an index is valid" in {
      Board.validIndex(0,0) should beTrue
      Board.validIndex(6,5) should beTrue
      Board.validIndex(0,6) should beFalse
      Board.validIndex(0,7) should beFalse
      Board.validIndex(7,0) should beFalse
    }

    "Set a value" in {
      val testBoard = Board
      val updatedBoard = testBoard.placeCell(0,0,Some(RedCell))
      updatedBoard.cellValue(0) shouldEqual Some(RedCell)
    }

    "Does not overwrite a value" in {
      val testBoard = Board
      val updatedGrid = testBoard.placeCell(0,0,Some(RedCell))
      val tryAnotherUpdate = updatedGrid.placeCell(0,0,Some(BlackCell))
      tryAnotherUpdate.cellValue(0) shouldEqual Some(RedCell)
    }

    "Build a list of horizontal indices" in {
      Board.horizontalIndices(5, 0) shouldEqual List((0,0), (1,0), (2,0), (3,0), (4,0), (5,0), (6,0), (7,0), (8,0), (9,0), (10,0))
    }

    "Build a list of vertical indices" in {
      Board.verticalIndices(0, 0) shouldEqual List((0,-5), (0,-4), (0,-3), (0,-2), (0,-1), (0,0), (0,1), (0,2), (0,3), (0,4), (0,5))
    }

    "Build a list of top left to bottom right diagonal indices" in {
      Board.diagonalTopLeftToBottomRightIndices(0, 0) shouldEqual List((-5,-5), (-4,-4), (-3,-3), (-2,-2), (-1,-1), (0,0), (1,1), (2,2), (3,3), (4,4), (5,5))
      Board.diagonalTopLeftToBottomRightIndices(0, 1) shouldEqual List((-5,-4), (-4,-3), (-3,-2), (-2,-1), (-1,0), (0,1), (1,2), (2,3), (3,4), (4,5), (5,6))
      Board.diagonalTopLeftToBottomRightIndices(1, 1) shouldEqual List((-4,-4), (-3,-3), (-2,-2), (-1,-1), (0,0), (1,1), (2,2), (3,3), (4,4), (5,5), (6,6))
    }

    "Build a list of bottom left to top right diagonal indices" in {
      Board.diagonalBottomLeftToTopRightIndices(0, 5) shouldEqual List((-5,10), (-4,9), (-3,8), (-2,7), (-1,6), (0,5), (1,4), (2,3), (3,2), (4,1), (5,0))
      Board.diagonalBottomLeftToTopRightIndices(1, 4) shouldEqual List((-4,9), (-3,8), (-2,7), (-1,6), (0,5), (1,4), (2,3), (3,2), (4,1), (5,0), (6,-1))
    }

    "Detect a horizontal win" in {
      val board = Board
      val board2 = board.placeCell(0,0,Some(RedCell))
      val board3 = board2.placeCell(3,0,Some(RedCell))
      val board4 = board3.placeCell(1,0,Some(RedCell))
      val board5 = board4.placeCell(2,0,Some(RedCell))
      board5.winningMove(2,0,RedCell) should beTrue
    }

    "Detect a vertical win" in {
      val board = Board
      val board2 = board.placeCell(0,3,Some(RedCell))
      val board3 = board2.placeCell(0,2,Some(RedCell))
      val board4 = board3.placeCell(0,1,Some(RedCell))
      val board5 = board4.placeCell(0,0,Some(RedCell))
      board5.winningMove(0,0,RedCell) should beTrue
    }

    "Detect a diagonal win" in {
      val board = Board
      val board2 = board.placeCell(0,2,Some(RedCell))
      val board3 = board2.placeCell(1,3,Some(RedCell))
      val board4 = board3.placeCell(3,5,Some(RedCell))
      val board5 = board4.placeCell(2,4,Some(RedCell))
      board5.winningMove(2,4,RedCell) should beTrue
    }

    "Check if a column is playable" in {
      val board = Board
      val board2 = board.placeCell(0,0,Some(RedCell))
      val board3 = board2.placeCell(0,1,Some(BlackCell))
      val board4 = board3.placeCell(0,2,Some(RedCell))
      val board5 = board4.placeCell(0,3,Some(BlackCell))
      val board6 = board5.placeCell(0,4,Some(RedCell))
      val board7 = board6.placeCell(0,5,Some(BlackCell))
      board7.canPlayColumn(0) should beFalse

      val anotherboard = Board
      anotherboard.canPlayColumn(0) should beTrue
    }

    "Find a column from an index" in {
      val board = Board
      board.columnFromIndex(13) shouldEqual 6
      board.columnFromIndex(15) shouldEqual 1
      board.columnFromIndex(17) shouldEqual 3
      board.columnFromIndex(1) shouldEqual 1
      board.columnFromIndex(0) shouldEqual 0
      board.columnFromIndex(35) shouldEqual 0
    }

    "Find a row from an index" in {
      val board = Board
      board.rowFromIndex(0) shouldEqual 0
      board.rowFromIndex(6) shouldEqual 0
      board.rowFromIndex(15) shouldEqual 2
      board.rowFromIndex(41) shouldEqual 5
    }
  }

}

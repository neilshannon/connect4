package com.ntsdev.connect4.model

import org.specs2.mutable.Specification

class GridSpec extends Specification {

  "The Grid" should {
    "Calculate an index properly" in {
      val testGrid = Grid
      testGrid.cellIndex(0, 0) shouldEqual 0 //first column, first row
      testGrid.cellIndex(0, 1) shouldEqual 7 //first column, second row
      testGrid.cellIndex(6, 5) shouldEqual 41 //seventh column, sixth row
    }

    "Check if an index is valid" in {
      Grid.validIndex(0,0) should beTrue
      Grid.validIndex(6,5) should beTrue
      Grid.validIndex(0,6) should beFalse
      Grid.validIndex(0,7) should beFalse
      Grid.validIndex(7,0) should beFalse
    }

    "Set a value" in {
      val testGrid = Grid
      val updatedGrid = testGrid.placeCell(0,0,Some(RedCell))
      updatedGrid.cellValue(0) shouldEqual Some(RedCell)
    }

    "Does not overwrite a value" in {
      val testGrid = Grid
      val updatedGrid = testGrid.placeCell(0,0,Some(RedCell))
      val tryAnotherUpdate = updatedGrid.placeCell(0,0,Some(BlackCell))
      tryAnotherUpdate.cellValue(0) shouldEqual Some(RedCell)
    }

    "Build a list of horizontal indices" in {
      Grid.horizontalIndices(0) shouldEqual List((0,0), (1,0), (2,0), (3,0), (4,0), (5,0), (6,0))
    }

    "Build a list of vertical indices" in {
      Grid.verticalIndices(0) shouldEqual List((0,0), (0,1), (0,2), (0,3), (0,4), (0,5))
    }

    "Build a list of top left to bottom right diagonal indices" in {
      Grid.diagonalTopLeftToBottomRightIndices(0, 0) shouldEqual List((0,0), (1,1), (2,2), (3,3), (4,4))
      Grid.diagonalTopLeftToBottomRightIndices(0, 1) shouldEqual List((0,1), (1,2), (2,3), (3,4), (4,5))
      Grid.diagonalTopLeftToBottomRightIndices(1, 1) shouldEqual List((1,1), (2,2), (3,3), (4,4), (5,5))
    }

    "Build a list of bottom left to top right diagonal indices" in {
      Grid.diagonalBottomLeftToTopRightIndices(0, 5) shouldEqual List((0,5), (1,4), (2,3), (3,2), (4,1))
      Grid.diagonalBottomLeftToTopRightIndices(1, 4) shouldEqual List((1,4), (2,3), (3,2), (4,1), (5,0))
    }

    "Build a list of top right to bottom left diagonal indices" in {
      Grid.diagonalTopRightToBottomLeftIndices(6, 0) shouldEqual List((6,0), (5,1), (4,2), (3,3), (2,4))
      Grid.diagonalTopRightToBottomLeftIndices(6, 1) shouldEqual List((6,1), (5,2), (4,3), (3,4), (2,5))
    }

    "Build a list of bottom right to top left diagonal indices" in {
      Grid.diagonalBottomRightToTopLeftIndices(6, 5) shouldEqual List((6,5), (5,4), (4,3), (3,2), (2,1))
      Grid.diagonalBottomRightToTopLeftIndices(6, 4) shouldEqual List((6,4), (5,3), (4,2), (3,1), (2,0))
    }
  }

}

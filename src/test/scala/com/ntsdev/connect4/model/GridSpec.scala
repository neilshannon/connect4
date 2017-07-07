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

    "Build a list of horizontal indexes" in {
      Grid.horizontalIndices(0) shouldEqual List((0,0), (1,0), (2,0), (3,0), (4,0), (5,0), (6,0))
    }

    "Build a list of vertical indexes" in {
      Grid.verticalIndices(0) shouldEqual List((0,0), (0,1), (0,2), (0,3), (0,4), (0,5))
    }
  }

}

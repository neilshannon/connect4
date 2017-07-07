package com.ntsdev.connect4.ai

import com.ntsdev.connect4.com.ntsdev.connect4.ai.SimpleComputerPlayer
import com.ntsdev.connect4.model.Grid
import org.specs2.mutable.Specification

class ComputerPlayerSpec extends Specification {
  "the simple computer player" should {
    "generate a move" in {
      val testGrid = Grid

      val computerPlayer = new SimpleComputerPlayer
      val moveCoords = computerPlayer.nextMove(testGrid)
      moveCoords shouldEqual (0,0)
    }
  }
}

package com.ntsdev.connect4.ai

import com.ntsdev.connect4.com.ntsdev.connect4.ai.SimpleComputerPlayer
import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.{Grid, RedCell}
import org.specs2.mutable.Specification

class ComputerPlayerSpec extends Specification {
  "the simple computer player" should {
    "generate a move" in {
      val testGrid = Grid
      val testGame = new Game(testGrid)

      val computerPlayer = new SimpleComputerPlayer
      val testGrid2 = testGrid.placeCell(0,0,Some(RedCell))
      val testGame2 = new Game(testGrid2)
      val column = computerPlayer.nextMove(testGame2)
      column shouldEqual 0
    }
  }
}

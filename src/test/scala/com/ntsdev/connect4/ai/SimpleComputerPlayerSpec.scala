package com.ntsdev.connect4.ai

import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.{Board, RedCell}
import org.specs2.mutable.Specification

class SimpleComputerPlayerSpec extends Specification {
  "the simple computer player" should {
    "generate a move" in {
      val testBoard = Board
      val computerPlayer = new SimpleComputerPlayer
      val testBoard2 = testBoard.placeCell(0,0,Some(RedCell))
      val testGame = new Game(testBoard2)
      val column = computerPlayer.nextMove(testGame)
      column shouldEqual 0
    }
  }
}

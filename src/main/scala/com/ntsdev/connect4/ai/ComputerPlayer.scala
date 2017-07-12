package com.ntsdev.connect4.ai

import com.ntsdev.connect4.game.Game

/**
  * A computer player.  Calculates the next move based on the current game state.
  */
abstract class ComputerPlayer {
  def nextMove(board: Game): Int
}

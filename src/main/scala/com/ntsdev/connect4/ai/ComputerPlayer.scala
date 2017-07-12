package com.ntsdev.connect4.ai

import com.ntsdev.connect4.game.Game

abstract class ComputerPlayer {
    def nextMove(board: Game): Int
}

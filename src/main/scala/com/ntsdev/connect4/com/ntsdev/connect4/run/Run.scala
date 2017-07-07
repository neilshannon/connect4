package com.ntsdev.connect4.com.ntsdev.connect4.run

import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.Grid


object Run {

  def main(args: Array[String]): Unit = {
    val game = new Game(Grid)
    game.runGame
  }

}

package com.ntsdev.connect4.ai

import com.ntsdev.connect4.game.Game
import com.ntsdev.connect4.model.{BlackCell, RedCell}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class AdvancedComputerPlayer {

  case class Result(score: Int, column: Int){
    override def toString: String = "Score: [" + score + "] Column: [" + column + "]"
  }

  private final val MAX_DEPTH = 4

  private var results = mutable.ListBuffer[Result]()

  /**
    * Pick a next move for the computer
    *
    * @param board the current game board
    * @return the column the computer should play
    */
  def nextMove(board: Game): Int = {
    results = ListBuffer[Result]()
    val score = minimax("BLACK", board, 0)
    val move = getBestColumn(score)
    move
  }

  /**
    * Finds the best calculated column for the computer to play
    *
    * @param bestScore the calculated score of the move
    * @return the best column
    */
  private def getBestColumn(bestScore: Int): Int = {
    import scala.util.control.Breaks._
    var best: Result = null
    breakable {
      for (result <- results) {
        if (result.score == bestScore) {
          best = result
          break
        }
      }
    }
    best.column
  }

  private def calcMin(scores: List[Int]): Int = scores.min

  private def calcMax(scores: List[Int]): Int = scores.max

  /**
    * Implementation of minimax algorithm to minimize chance of winning with the human player's moves and maximize the
    * chance of winning by the computer's moves.  Used to select the best move for the computer to make.
    * reference: http://en.wikipedia.org/wiki/Minimax
    *
    * @param player which player's moves should be calculated
    * @param game  the game
    * @param depth  recursion depth (depth of game search tree)
    * @return the best score for the computer
    */
  private def minimax(player: String, game: Game, depth: Int): Int = {
    var scores = mutable.ListBuffer[Int]()
    val moves = game.getAvailableColumns
    if (game.winningPlayer == "BLACK") return 1000
    if (game.winningPlayer == "RED") return 0
    if (moves.isEmpty || depth == MAX_DEPTH) return 500

    for (column <- moves) {
      val row = game.grid.nextCellForColumn(column)

      if (player == "BLACK") {
        //make computer move
        val newGrid = game.grid.placeCell(column, row, Some(BlackCell))
        val winningPlayer = if(newGrid.win) "BLACK" else ""
        val newGame = new Game(newGrid, winningPlayer)

        println("Computer [" + column + "], [" + row + "], depth: [" + depth + "]")

        val currentScore = minimax("RED", newGame, depth + 1)
        scores += currentScore
        if (depth == 0){
          results += Result(currentScore, column)
        }
      }
      else {
        val newGrid = game.grid.placeCell(column, row, Some(RedCell)) //make the move for the human player

        val winningPlayer = if(newGrid.win) "RED" else ""
        val newGame = new Game(newGrid, winningPlayer)

        println("Player [" + column + "], [" + row + "]")

        val currentScore = minimax("BLACK", newGame, depth + 1)
        scores += currentScore
      }
      game.grid.placeCell(column, row, None) //undo move
    }

    if (player eq "BLACK") {
      calcMax(scores.toList) //maximize computer score
    }
    else{
      calcMin(scores.toList) //minimize user score
    }
  }

}

object AdvancedComputerPlayer {
  def apply(): AdvancedComputerPlayer = {
    new AdvancedComputerPlayer
  }
}

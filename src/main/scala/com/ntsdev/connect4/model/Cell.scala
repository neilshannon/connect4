package com.ntsdev.connect4.model

sealed abstract class Cell

object Cell {
  def fromString(s: String): Option[Cell] = {
    s match {
      case "[R]" => Some(RedCell)
      case "[B]" => Some(BlackCell)
      case "-" => None
      case _ => None
    }
  }
}

class RedCell extends Cell {
  override def toString = "[R]"
}
object RedCell extends RedCell

class BlackCell extends Cell {
  override def toString = "[B]"
}
object BlackCell extends BlackCell

package com.ntsdev.connect4.model

sealed class Cell

class RedCell extends Cell {
  override def toString = "[R]"
}
object RedCell extends RedCell

class BlackCell extends Cell {
  override def toString = "[B]"
}
object BlackCell extends BlackCell

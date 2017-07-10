package com.ntsdev.connect4.wire

import com.ntsdev.connect4.model.{BlackCell, RedCell}
import org.json4s.{DefaultFormats, Formats}
import org.specs2.mutable.Specification

class OptionCellSerializerSpec extends Specification {

  private implicit lazy val jsonFormats: Formats = DefaultFormats + new OptionCellSerializer
  private val serialization = org.json4s.jackson.Serialization

  "the option cell serializer" should {
    "serialize an Option[Cell]" in {
      val redCell = RedCell
      val listOfOptionCell = List(Some(redCell))
      val redJson = serialization.write(listOfOptionCell)
      redJson shouldEqual "[{\"cell\":\"[R]\"}]"

      val blackCell = BlackCell
      val listOfBlackOptionCell = List(Some(blackCell))
      val blackJson = serialization.write(listOfBlackOptionCell)
      blackJson shouldEqual "[{\"cell\":\"[B]\"}]"

      val listOfBlankOptionCell = List(None)
      val blankJson = serialization.write(listOfBlankOptionCell)
      blankJson shouldEqual "[{\"cell\":\"[-]\"}]"

    }
  }
}

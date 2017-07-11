package com.ntsdev.connect4.wire

import com.ntsdev.connect4.model.Cell
import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JField, JObject, JString}


/**
  * Custom serializer for Option[Cell] => JSON => Option[Cell]
  */
class OptionCellSerializer extends CustomSerializer[Option[Cell]](format => (
  {
    case JObject(JField("cell", JString(s)) :: Nil) =>
      Cell.fromString(s)
  },
  {
    case x: Option[_] =>
      if(x.isDefined) {
        val value = x.get.toString
        JObject(JField("cell", JString(value)))
      } else {
        JObject(JField("cell", JString("[-]")))
      }

  }
))

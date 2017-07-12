package com.ntsdev.connect4.web

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.test.specs2.MutableScalatraSpec

class Connect4APISpec extends MutableScalatraSpec {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  addServlet(classOf[Connect4API], "/*")

  "GET /startGame" should {
    "begin a game and return the board" in {
        get("/startGame?advanced=true") {
          status must_== 200
        }
    }

    "make a move" in {
      val postBody =
        """
          |[{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},
          |{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},
          |{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},
          |{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},
          |{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},
          |{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"},{"cell":"[-]"}]
        """.stripMargin

      post("/makeMove?column=0", postBody, Map("Content-Type" -> "application/json")){
        status must_== 200
      }
    }
  }
}
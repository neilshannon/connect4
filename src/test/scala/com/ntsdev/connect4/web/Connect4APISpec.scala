package com.ntsdev.connect4.web

import org.scalatra.test.specs2.MutableScalatraSpec;
import org.json4s.{DefaultFormats, Formats}
import org.json4s._
import org.json4s.jackson.JsonMethods._

class Connect4APISpec extends MutableScalatraSpec {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  addServlet(classOf[Connect4API], "/*")

  "GET /startGame" should {
    "begin a game and return the board" in {
        get("/startGame?advanced=true") {
          //val json = parse(response.body)
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
        //val json = parse(response.body)
        status must_== 200
      }
    }
  }
}
package models

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class Hello(message: String)

object Hello {
  implicit val helloFormat: OFormat[Hello] = Json.format[Hello]
}

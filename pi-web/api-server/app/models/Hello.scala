package models

import play.api.libs.json._

case class Hello(message: String)

object Hello {
  implicit val helloFormat: OFormat[Hello] = Json.format[Hello]
}

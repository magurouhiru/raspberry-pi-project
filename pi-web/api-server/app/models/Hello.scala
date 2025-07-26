package models

import java.time.Instant

import play.api.libs.json._

case class Hello(id: Int, message: String, timestamp: Instant)

object Hello {
  implicit val format: OFormat[Hello] = Json.format[Hello]
}

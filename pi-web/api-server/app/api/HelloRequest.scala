package api

import java.time.LocalDateTime

import play.api.libs.json._

case class HelloRequest(message: String)

object HelloRequest {
  implicit val helloFormat: OFormat[HelloRequest] = Json.format[HelloRequest]
}

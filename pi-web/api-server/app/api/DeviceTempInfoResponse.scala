package api

import java.time.LocalDateTime

import play.api.libs.json._

case class DeviceTempInfoResponse(timestamp: LocalDateTime, temp: Int)

object DeviceTempInfoResponse {
  implicit val helloFormat: OFormat[DeviceTempInfoResponse] =
    Json.format[DeviceTempInfoResponse]
}

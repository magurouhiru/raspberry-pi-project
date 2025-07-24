package api

import java.time.Instant

import play.api.libs.json._

case class DeviceTempInfoResponse(timestamp: Instant, temp: Int)

object DeviceTempInfoResponse {
  implicit val helloFormat: OFormat[DeviceTempInfoResponse] =
    Json.format[DeviceTempInfoResponse]
}

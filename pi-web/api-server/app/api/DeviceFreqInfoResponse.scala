package api

import java.time.LocalDateTime

import play.api.libs.json._

case class DeviceFreqInfoResponse(timestamp: LocalDateTime, freq: Int)

object DeviceFreqInfoResponse {
  implicit val helloFormat: OFormat[DeviceFreqInfoResponse] =
    Json.format[DeviceFreqInfoResponse]
}

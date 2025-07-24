package api

import java.time.Instant

import play.api.libs.json._

case class DeviceFreqInfoResponse(timestamp: Instant, freq: Int)

object DeviceFreqInfoResponse {
  implicit val helloFormat: OFormat[DeviceFreqInfoResponse] =
    Json.format[DeviceFreqInfoResponse]
}

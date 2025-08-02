package models

import java.time.Instant

import play.api.libs.json._

case class DeviceFreqInfo(id: Int, timestamp: Instant, freq: Int)

object DeviceFreqInfo {
  implicit val format: OFormat[DeviceFreqInfo] = Json.format[DeviceFreqInfo]
}

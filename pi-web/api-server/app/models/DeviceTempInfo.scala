package models

import java.time.Instant

import play.api.libs.json._

case class DeviceTempInfo(id: Int, timestamp: Instant, temp: Int)

object DeviceTempInfo {
  implicit val format: OFormat[DeviceTempInfo] = Json.format[DeviceTempInfo]
}

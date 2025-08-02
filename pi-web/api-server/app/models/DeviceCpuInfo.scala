package models

import java.time.Instant

import play.api.libs.json._

case class DeviceCpuInfo(id: Int, timestamp: Instant)

object DeviceCpuInfo {
  implicit val format: OFormat[DeviceCpuInfo] = Json.format[DeviceCpuInfo]
}

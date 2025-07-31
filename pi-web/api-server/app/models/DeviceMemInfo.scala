package models

import java.time.Instant

import play.api.libs.json._

case class DeviceMemInfo(
    id: Int,
    timestamp: Instant,
    mem_total: Int,
    mem_free: Int,
    buffers: Int,
    cached: Int,
    active: Int,
    inactive: Int,
)

object DeviceMemInfo {
  implicit val format: OFormat[DeviceMemInfo] = Json.format[DeviceMemInfo]
}

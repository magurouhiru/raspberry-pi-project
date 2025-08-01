package api

import java.time.Instant

import play.api.libs.json._

case class DeviceMemInfoResponse(
    timestamp: Instant,
    mem_total: Int,
    mem_free: Int,
    buffers: Int,
    cached: Int,
    active: Int,
    inactive: Int,
)

object DeviceMemInfoResponse {
  implicit val helloFormat: OFormat[DeviceMemInfoResponse] =
    Json.format[DeviceMemInfoResponse]
}

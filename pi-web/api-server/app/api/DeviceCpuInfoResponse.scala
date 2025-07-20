package api

import java.time.LocalDateTime

import play.api.libs.json._

case class DeviceCpuInfoResponse(
    timestamp: LocalDateTime,
    cpu: DeviceCpuDetailInfoResponse,
    cpu0: DeviceCpuDetailInfoResponse,
    cpu1: DeviceCpuDetailInfoResponse,
    cpu2: DeviceCpuDetailInfoResponse,
    cpu3: DeviceCpuDetailInfoResponse,
)

object DeviceCpuInfoResponse {
  implicit val helloFormat: OFormat[DeviceCpuInfoResponse] =
    Json.format[DeviceCpuInfoResponse]
}

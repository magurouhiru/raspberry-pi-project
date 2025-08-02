package api

import java.time.Instant

import play.api.libs.json._

case class DeviceCpuInfoResponse(
    timestamp: Instant,
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

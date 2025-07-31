package api

import java.time.Instant

import play.api.libs.json._

case class DBDeviceCpuInfoResponse(
    id: Int,
    timestamp: Instant,
    cpu: DeviceCpuDetailInfoResponse,
    cpu0: DeviceCpuDetailInfoResponse,
    cpu1: DeviceCpuDetailInfoResponse,
    cpu2: DeviceCpuDetailInfoResponse,
    cpu3: DeviceCpuDetailInfoResponse,
)

object DBDeviceCpuInfoResponse {
  implicit val format: OFormat[DBDeviceCpuInfoResponse] =
    Json.format[DBDeviceCpuInfoResponse]
}

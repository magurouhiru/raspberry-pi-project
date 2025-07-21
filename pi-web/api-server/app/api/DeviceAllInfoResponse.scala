package api

import play.api.libs.json._

case class DeviceAllInfoResponse(
    temp: DeviceTempInfoResponse,
    freq: DeviceFreqInfoResponse,
    cpu: DeviceCpuInfoResponse,
    mem: DeviceMemInfoResponse,
)

object DeviceAllInfoResponse {
  implicit val helloFormat: OFormat[DeviceAllInfoResponse] =
    Json.format[DeviceAllInfoResponse]
}

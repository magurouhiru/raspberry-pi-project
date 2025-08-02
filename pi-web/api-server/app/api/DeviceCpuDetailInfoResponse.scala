package api

import play.api.libs.json._

case class DeviceCpuDetailInfoResponse(total: Int, idle: Int)

object DeviceCpuDetailInfoResponse {
  implicit val helloFormat: OFormat[DeviceCpuDetailInfoResponse] =
    Json.format[DeviceCpuDetailInfoResponse]
}

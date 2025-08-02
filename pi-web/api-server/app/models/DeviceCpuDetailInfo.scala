package models

import play.api.libs.json._

case class DeviceCpuDetailInfo(
    id: Int,
    cpu_info_id: Int,
    cpu_number: Int,
    total: Int,
    idle: Int,
)

object DeviceCpuDetailInfo {
  implicit val format: OFormat[DeviceCpuDetailInfo] =
    Json.format[DeviceCpuDetailInfo]
}

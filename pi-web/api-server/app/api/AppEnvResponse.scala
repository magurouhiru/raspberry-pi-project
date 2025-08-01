package api

import play.api.libs.json._

case class AppEnvResponse(
    api_server: ApiServerAppEnvResponse,
    device_server: Option[DeviceServerAppEnvResponse],
)

object AppEnvResponse {
  implicit val helloFormat: OFormat[AppEnvResponse] = Json.format[AppEnvResponse]
}

package api

import play.api.libs.json._

case class DeviceServerAppEnvResponse(APP_ENV: String, MOCK_MODE: Boolean)

object DeviceServerAppEnvResponse {
  implicit val helloFormat: OFormat[DeviceServerAppEnvResponse] =
    Json.format[DeviceServerAppEnvResponse]
}

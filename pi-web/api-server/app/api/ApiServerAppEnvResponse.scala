package api

import play.api.libs.json._

case class ApiServerAppEnvResponse(APP_ENV: String)

object ApiServerAppEnvResponse {
  implicit val helloFormat: OFormat[ApiServerAppEnvResponse] =
    Json.format[ApiServerAppEnvResponse]
}

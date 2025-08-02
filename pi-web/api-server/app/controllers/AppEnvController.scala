package controllers

import javax.inject._

import scala.concurrent.ExecutionContext

import play.api.Configuration
import play.api.libs.json._
import play.api.mvc._

import api.ApiServerAppEnvResponse
import api.AppEnvResponse
import services.DeviceApiService

@Singleton
class AppEnvController @Inject() (
    cc: ControllerComponents,
    service: DeviceApiService,
    config: Configuration,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {
  private val apiServerAppEnv =
    ApiServerAppEnvResponse(config.get[String]("app.env"))

  def getAppEnv: Action[AnyContent] = Action.async(
    service
      .getAppEnv
      .map {
        case Right(value) =>
          Ok(Json.toJson(AppEnvResponse(apiServerAppEnv, Some(value))))
        case Left(_) => Ok(Json.toJson(AppEnvResponse(apiServerAppEnv, None)))
      },
  )

}

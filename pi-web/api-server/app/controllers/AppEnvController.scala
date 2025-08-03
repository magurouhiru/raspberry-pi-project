package controllers

import javax.inject._

import play.api.Configuration
import play.api.libs.json._
import play.api.mvc._

import api.ApiServerAppEnvResponse

@Singleton
class AppEnvController @Inject() (
    cc: ControllerComponents,
    config: Configuration,
) extends AbstractController(cc) {
  private val apiServerAppEnv = ApiServerAppEnvResponse(
    config.get[String]("app.env"),
    config.get[String]("device.mock") == "true",
  )

  def getAppEnv: Action[AnyContent] = Action(Ok(Json.toJson(apiServerAppEnv)))

}

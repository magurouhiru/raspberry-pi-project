package controllers

import javax.inject._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import play.api.libs.json._
import play.api.mvc._

import services.HelloDBService

@Singleton
class HelloController @Inject() (
    cc: ControllerComponents,
    service: HelloDBService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def get(): Action[AnyContent] = Action.async(
    service
      .create(api.HelloRequest("hello"))
      .map {
        case Right(hello) => Ok(Json.toJson(hello))
        case Left(exception) => InternalServerError(exception.getMessage)
      },
  )

  def echo(): Action[JsValue] = Action.async(parse.json)(implicit request =>
    request.body.validate[api.HelloRequest] match {
      case JsSuccess(req, _) =>
        // 成功: HelloをそのままJSONで返す
        service
          .create(req)
          .map {
            case Right(hello) => Ok(Json.toJson(hello))
            case Left(exception) => InternalServerError(exception.getMessage)
          }
      case JsError(errors) =>
        // 失敗: 400 Bad Request
        Future(BadRequest(
          Json.obj("status" -> "error", "message" -> JsError.toJson(errors)),
        ))
    },
  )

}

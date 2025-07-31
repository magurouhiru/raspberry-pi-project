package controllers

import javax.inject._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.libs.json._
import play.api.mvc._

import cats.data.EitherT

import models.Hello
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
        case Left(error) => Status(error.status)(error.error)
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
            case Left(error) => Status(error.status)(error.error)
          }
      case JsError(errors) =>
        // 失敗: 400 Bad Request
        Future(BadRequest(
          Json.obj("status" -> "error", "message" -> JsError.toJson(errors)),
        ))
    },
  )

  def read(offset: Option[Int], limit: Option[Int]): Action[AnyContent] = Action
    .async {
      val res = for {
        hellos <- EitherT(service.read(offset.getOrElse(0), limit.getOrElse(5)))
        size <- EitherT(service.readSize())
      } yield (hellos, size)

      res
        .value
        .map {
          case Right((hellos, size)) =>
            Ok(Json.toJson(hellos)).withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        }
    }

}

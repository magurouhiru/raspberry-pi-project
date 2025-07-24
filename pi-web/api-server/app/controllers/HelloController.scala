package controllers

import javax.inject._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import play.api.libs.json._
import play.api.mvc._

import models.Hello
import services.HelloDBService

@Singleton
class HelloController @Inject() (
    cc: ControllerComponents,
    service: HelloDBService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  // ヘルパー: Try[R] を Result に変換
  implicit class TryResult[R](t: Try[R]) {
    def toResult(implicit writes: Writes[R]): Result = t match {
      case Success(value) => Ok(Json.toJson(value))
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }

  // ヘルパー: JsResult[T] を Future[Result] に変換
  implicit class JsResultOps[T](jsResult: JsResult[T]) {
    def toFutureResult(
        f: T => Future[Try[T]],
    )(implicit writes: Writes[T]): Future[Result] = jsResult match {
      case JsSuccess(value, _) => f(value).map(_.toResult)
      case JsError(errors) => Future.successful(BadRequest(
          Json.obj("status" -> "error", "message" -> JsError.toJson(errors)),
        ))
    }
  }

  def get(): Action[AnyContent] = Action.async(
    service
      .create(Hello("hello"))
      .map {
        case Success(_) => Ok(Json.toJson(Hello("hello")))
        case Failure(exception) => InternalServerError(exception.getMessage)
      },
  )

  def echo(): Action[JsValue] = Action.async(parse.json)(implicit request =>
    request.body.validate[Hello].toFutureResult(service.create),
  )

}

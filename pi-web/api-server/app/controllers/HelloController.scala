package controllers

import javax.inject._

import play.api.libs.json._
import play.api.mvc._

import models.Hello
import repositories.HelloRepository

@Singleton
class HelloController @Inject() (cc: ControllerComponents, repo: HelloRepository)
    extends AbstractController(cc) {

  def get(): Action[AnyContent] = Action {
    repo.i(Hello("hello"))
    Ok(Json.toJson(Hello("hello")))
  }

  def echo(): Action[JsValue] = Action(parse.json)(implicit request =>
    // JSONからHelloに変換
    request.body.validate[Hello] match {
      case JsSuccess(hello, _) =>
        // 成功: HelloをそのままJSONで返す
        repo.i(hello)
        Ok(Json.toJson(hello))
      case JsError(errors) =>
        // 失敗: 400 Bad Request
        BadRequest(
          Json.obj("status" -> "error", "message" -> JsError.toJson(errors)),
        )
    },
  )

}

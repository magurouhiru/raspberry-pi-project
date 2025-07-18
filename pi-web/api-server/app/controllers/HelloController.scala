package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class HelloController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  case class Hello(message: String)
  implicit val helloFormat: OFormat[Hello] = Json.format[Hello]

  def get(): Action[AnyContent] = Action {
    Ok(Json.toJson(Hello("hello")))
  }

  def echo(): Action[JsValue] = Action(parse.json) { implicit request =>
    // JSONからHelloに変換
    request.body.validate[Hello] match {
      case JsSuccess(hello, _) =>
        // 成功: HelloをそのままJSONで返す
        Ok(Json.toJson(hello))
      case JsError(errors) =>
        // 失敗: 400 Bad Request
        BadRequest(
          Json.obj("status" -> "error", "message" -> JsError.toJson(errors))
        )
    }
  }

}

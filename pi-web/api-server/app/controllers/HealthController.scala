package controllers

import javax.inject._

import play.api.libs.json._
import play.api.mvc._

@Singleton
class HealthController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  case class Hello(message: String)

  implicit val helloFormat: OFormat[Hello] = Json.format[Hello]

  def health(): Action[AnyContent] = Action(Ok(""))

  def ready(): Action[AnyContent] = Action(Ok(""))

}

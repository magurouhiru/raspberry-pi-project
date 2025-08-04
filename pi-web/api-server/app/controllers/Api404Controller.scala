package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class Api404Controller @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  def error404(path: String): Action[AnyContent] =
    Action(NotFound(s"API path not found: /api/$path"))

}

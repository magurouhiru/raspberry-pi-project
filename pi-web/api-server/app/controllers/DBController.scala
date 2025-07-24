package controllers

import javax.inject._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.libs.json._
import play.api.mvc._

import cats.data.EitherT

import api._
import models.Hello
import services.HelloDBService

@Singleton
class DBController @Inject() (
    cc: ControllerComponents,
    helloService: HelloDBService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def anyTables(): Action[AnyContent] = Action.async {
    val tables: EitherT[Future, Exception, Seq[Hello]] =
      for { hello <- EitherT(helloService.readAll()) } yield hello

    tables
      .value
      .map {
        case Right(hellos) =>
          val hello = AnyTable.toAnyTableFromHello(hellos)
          Ok(Json.toJson(AnyTablesResponse(Seq(hello))))
        case Left(exception) => InternalServerError(exception.toString)
      }
  }

}

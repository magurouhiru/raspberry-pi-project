package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.jdk.javaapi.FutureConverters

import play.api.http.Status
import play.api.libs.json._
import play.libs.ws._

class ApiServiceBase @Inject() (ws: WSClient)(implicit ec: ExecutionContext) {

  def get[T](url: String)(implicit fjs: Reads[T]): Future[Either[ApiError, T]] =
    FutureConverters
      .asScala[WSResponse](ws.url(url).get())
      .map(res =>
        if (res.getStatus == Status.OK) Right(Json.parse(res.getBody).as[T])
        else Left(ApiError(res.getStatus, res.getBody)),
      )
      .recover { case e: Exception =>
        Left(ApiError(Status.INTERNAL_SERVER_ERROR, e.toString))
      }

}

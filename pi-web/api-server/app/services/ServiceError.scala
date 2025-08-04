package services

import scala.language.implicitConversions

import play.api.http.Status

case class ServiceError(status: Int, error: String)

object ServiceError {
  implicit def throwableToServiceError(e: Throwable): ServiceError =
    ServiceError(Status.INTERNAL_SERVER_ERROR, e.toString)
}

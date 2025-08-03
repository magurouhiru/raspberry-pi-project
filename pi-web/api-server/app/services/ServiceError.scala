package services

import play.api.http.Status

import scala.language.implicitConversions

case class ServiceError(status: Int, error: String)

object ServiceError {
  implicit def throwableToServiceError(e: Throwable): ServiceError =
    ServiceError(Status.INTERNAL_SERVER_ERROR, e.toString)
}

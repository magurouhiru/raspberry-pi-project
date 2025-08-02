package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.http.Status

import models.QuillContext

class DBServiceBase @Inject() (implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) {
  import quillCtx.ctx._

  def runAsyncTx[R](f: => R): Future[Either[ServiceError, R]] =
    Future(Right(transaction(f))).recover { case e: Exception =>
      Left(ServiceError(Status.INTERNAL_SERVER_ERROR, e.toString))
    }

}

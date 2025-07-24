package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import cats.syntax.either._

import models.QuillContext

class DBServiceBase @Inject() (implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) {
  import quillCtx.ctx._

  def runAsyncTx[R](f: => R): Future[Either[Exception, R]] =
    Future(Either.catchOnly[Exception](transaction(f)))

}

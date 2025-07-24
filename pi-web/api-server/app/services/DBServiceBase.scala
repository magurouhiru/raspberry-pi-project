package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Try

import models.QuillContext

class DBServiceBase @Inject() (implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) {
  import quillCtx.ctx._

  def runAsync[R](f: => R): Future[Try[R]] = Future(Try(f))

  def runAsyncTx[R](f: => R): Future[Try[R]] = Future(Try(transaction(f)))

}

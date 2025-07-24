package services

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Try

import models.Hello
import models.QuillContext
import repositories.HelloRepository

@Singleton
class HelloDBService @Inject() (repo: HelloRepository)(implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) extends DBServiceBase {

  def ready(): Future[Either[Exception, Long]] = runAsyncTx(repo.ready())

  def readAll(): Future[Either[Exception, Seq[Hello]]] = runAsyncTx(repo.readAll())

  def create(
      hello: api.HelloRequest,
  ): Future[Either[Exception, Option[Hello]]] =
    runAsyncTx(repo.read(repo.create(hello)))

}

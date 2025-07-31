package services

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import models.Hello
import models.QuillContext
import repositories.HelloRepository

@Singleton
class HelloDBService @Inject() (repo: HelloRepository)(implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) extends DBServiceBase {

  def readSize(): Future[Either[ServiceError, Long]] = runAsyncTx(repo.readSize())

  def read(offset: Int, limit: Int): Future[Either[ServiceError, Seq[Hello]]] =
    runAsyncTx(repo.read(offset, limit))

  def readAll(): Future[Either[ServiceError, Seq[Hello]]] =
    runAsyncTx(repo.readAll())

  def create(
      hello: api.HelloRequest,
  ): Future[Either[ServiceError, Option[Hello]]] =
    runAsyncTx(repo.read(repo.create(hello)))

}

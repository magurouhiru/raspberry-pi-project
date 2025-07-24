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

  def ready(): Future[Try[Long]] = runAsyncTx(repo.ready())

  def readAll(): Future[Try[Seq[Hello]]] = runAsyncTx(repo.readAll())

  def create(hello: Hello): Future[Try[Hello]] = runAsyncTx(repo.create(hello))

}

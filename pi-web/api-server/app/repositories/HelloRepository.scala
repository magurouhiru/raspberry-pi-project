package repositories

import javax.inject._

import models.Hello
import models.QuillContext

@Singleton
class HelloRepository @Inject() (quillCtx: QuillContext) {
  import quillCtx.ctx._

  def readSize(): Long = run(query[Hello].size)

  def read(id: Int): Option[Hello] = run(query[Hello].filter(_.id == lift(id)))
    .headOption

  def read(offset: Int, limit: Int): Seq[Hello] =
    run(query[Hello].drop(lift(offset)).take(lift(limit)))

  def readAll(): Seq[Hello] = run(query[Hello])

  def create(hello: api.HelloRequest): Int = run(
    query[Hello]
      .insert(_.message -> lift(hello.message))
      .returningGenerated(_.id),
  )

}

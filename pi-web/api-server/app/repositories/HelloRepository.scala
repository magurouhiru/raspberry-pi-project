package repositories

import javax.inject._

import models.Hello
import models.QuillContext

@Singleton
class HelloRepository @Inject() (quillCtx: QuillContext) {
  import quillCtx.ctx._

  def ready(): Long = run(query[Hello].size)

  def readAll(): Seq[Hello] = run(query[Hello])

  def create(hello: Hello): Hello = run(
    query[Hello]
      .insert(_.message -> lift(hello.message))
      .returningGenerated(r => Hello(r.message)),
  )

}

package repositories

import javax.inject._

import models.Hello
import models.QuillContext

@Singleton
class HelloRepository @Inject() (quillCtx: QuillContext) {
  import quillCtx.ctx._

  def q(): Seq[Hello] = run(query[Hello])

  def i(hello: Hello): Long = run(query[Hello].insertValue(lift(hello)))

}

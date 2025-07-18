package repositories

import javax.inject.Inject
import javax.inject.Singleton

import models.Hello
import models.QuillContext

@Singleton
class HelloRepository @Inject() (quillCtx: QuillContext) {
  import quillCtx.ctx._

  def q() = run(query[Hello])

  def i(hello: Hello) = run(query[Hello].insertValue(lift(hello)))

}

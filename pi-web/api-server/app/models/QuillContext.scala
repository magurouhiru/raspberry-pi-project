package models

import java.io.Closeable
import javax.inject._
import javax.sql.DataSource

import play.api.db.DBApi

import io.getquill._

@Singleton
class QuillContext @Inject() (dbApi: DBApi) {

  private val ds = dbApi
    .database("default")
    .dataSource
    .asInstanceOf[DataSource with Closeable]

  val ctx = new MysqlJdbcContext(SnakeCase, ds)
}

package models

import java.io.Closeable

import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

import play.api.db.DBApi

import io.getquill.PostgresJdbcContext
import io.getquill.SnakeCase

@Singleton
class QuillContext @Inject() (dbApi: DBApi) {
  private val ds = dbApi
    .database("default")
    .dataSource
    .asInstanceOf[DataSource with Closeable]

  val ctx = new PostgresJdbcContext(SnakeCase, ds)
}

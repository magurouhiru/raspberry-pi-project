package api

import play.api.libs.json._

case class AnyTablesResponse(tables: Seq[AnyTable])

object AnyTablesResponse {
  implicit val format: OFormat[AnyTablesResponse] =
    Json.format[AnyTablesResponse]
}

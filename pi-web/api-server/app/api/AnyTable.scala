package api

import play.api.libs.json._

case class AnyTable(name: String, label: Seq[String], data: Seq[Seq[String]])

object AnyTable {
  implicit val format: OFormat[AnyTable] = Json.format[AnyTable]

  def toAnyTableFromHello(hellos: Seq[models.Hello]): AnyTable = AnyTable(
    name = "hello",
    label = Seq("id", "message", "timestamp"),
    data = hellos.map(h => Seq(h.id.toString, h.message, h.timestamp.toString)),
  )
}

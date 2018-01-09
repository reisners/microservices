package valueobjects

import java.net.URI

import play.api.libs.json.{Format, Json}

case class Amount(unit: Option[String], value: Float)

object Amount {
  implicit val format: Format[Amount] = Json.format[Amount]
}

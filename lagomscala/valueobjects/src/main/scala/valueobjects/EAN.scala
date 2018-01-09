package valueobjects

import play.api.libs.json.{Format, Json}

case class EAN(val underlying: String) extends AnyVal

object EAN {

  implicit val format: Format[EAN] = Json.format[EAN]

}


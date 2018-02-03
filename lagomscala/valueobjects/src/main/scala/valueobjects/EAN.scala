package valueobjects

import play.api.libs.json._

case class EAN(val underlying: String) extends AnyVal

object EAN {

  implicit object eanFormat extends Format[EAN] {
    override def writes(o: EAN): JsValue = JsString(o.underlying)

    override def reads(json: JsValue): JsResult[EAN] = JsSuccess(new EAN(json.as[String]))
  }

}


package lagomscala.household.api

import java.time.Instant
import java.util.UUID

import play.api.libs.json.{Format, Json}
import valueobjects.{Amount, EAN}

import scala.collection.immutable.Seq

case class InventoryItem(id: Option[UUID],
                         productId: EAN,
                         originalAmount: Amount,
                         remainingFraction: Float,
                         obtained: Option[Instant],
                         bestBefore: Option[Instant]) {
}

object InventoryItem {

  implicit val format: Format[InventoryItem] = Json.format[InventoryItem]

}

/**
  * The Household Aggregate Root
  *
  * @param id
  * @param name
  * @param owner
  * @param inventoryItems
  */
case class Household(
                      id: Option[UUID],
                      name: Option[String],
                      owner: String,
                      inventoryItems: Seq[InventoryItem]) {

  def this(owner: String) = this(None, None, owner, Seq.empty)

}

object Household {

  implicit val format: Format[Household] = Json.format[Household]

}


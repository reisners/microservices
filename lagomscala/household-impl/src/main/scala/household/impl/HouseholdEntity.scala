package household.impl

import java.time.Instant
import java.util.UUID

import akka.Done
import com.lightbend.lagom.scaladsl.api.transport.{ExceptionMessage, PolicyViolation, TransportErrorCode, TransportException}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import lagomscala.household.api.{Household, InventoryItem}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq

/**
  * Created by srn7919 on 12.08.17.
  */
class HouseholdEntity extends PersistentEntity {
  override type Command = HouseholdCommand[_]
  override type Event = HouseholdEvent
  override type State = HouseholdState

  override def initialState = HouseholdState.newEntity

  override def behavior: Behavior = {
    case HouseholdState(None) => newEntity
    case HouseholdState(Some(household)) => existingEntity
  }

  def newEntity() : Actions = {
    Actions()
      // Empty state: None
      .onReadOnlyCommand[GetHousehold.type, Option[Household]] {
        case (GetHousehold, ctx, state) => ctx.reply(state.household)
      }
      .onCommand[CreateHousehold, Done] {
        case (CreateHousehold(household), ctx, state) =>
          ctx.thenPersist(HouseholdCreated(household))(_ => ctx.reply(Done))
      }
      .onEvent {
        case (HouseholdCreated(household), state) => state.copy(household = Some(household))
      }
  }



  /**
    * The FSM to return for existing user entities
    */
  def existingEntity: Actions = {
    Actions()
      /*
      Get or Create
       */
      .onReadOnlyCommand[GetHousehold.type, Option[Household]] {
        case (GetHousehold, ctx, state) => ctx.reply(state.household)
      }
      .onReadOnlyCommand[CreateHousehold, Done] {
        case (CreateHousehold(household), ctx, state) => ctx.invalidCommand("Household already exists!")
      }
      /*
      Consumption of inventory items
       */
      .onCommand[Consume, Done] {
        case (Consume(item, newRemainingFraction), ctx, state) if newRemainingFraction < 0 =>
          throw throw BadRequest("New remaining fraction cannot be less than zero")
        case (Consume(item, newRemainingFraction), ctx, state) if newRemainingFraction == 0 =>
          ctx.thenPersist(InventoryItemCompletelyConsumed(item))(_ => ctx.reply(Done))
        case (Consume(item, newRemainingFraction), ctx, state) =>
          ctx.thenPersist(InventoryItemPartiallyConsumed(item, newRemainingFraction))(_ => ctx.reply(Done))
      }
      /*
      Stocking of inventory items
       */
      .onCommand[Stock, Done] {
        case (Stock(item), ctx, state) => ctx.thenPersist(InventoryItemStocked(item))(_ => ctx.reply(Done))
      }

    /*
    Events
     */
      .onEvent {
        case (InventoryItemCompletelyConsumed(item), state) => state.remove(item)
        case (InventoryItemPartiallyConsumed(item, newRemainingFraction), state) => state.consume(item, newRemainingFraction)
        case (InventoryItemStocked(item), state) => state.stock(item)
      }
  }
}

// TODO: move / wrong place (why doesn't lagom include this exception anyway?)
final class BadRequest(errorCode: TransportErrorCode, exceptionMessage: ExceptionMessage, cause: Throwable) extends TransportException(errorCode, exceptionMessage, cause) {
  def this(errorCode: TransportErrorCode, exceptionMessage: ExceptionMessage) = this(errorCode, exceptionMessage, null)
}
object BadRequest {
  val ErrorCode = TransportErrorCode.BadRequest

  def apply(message: String) = new BadRequest(
    ErrorCode,
    new ExceptionMessage(classOf[BadRequest].getSimpleName, message), null
  )

  def apply(cause: Throwable) = new PolicyViolation(
    ErrorCode,
    new ExceptionMessage(classOf[BadRequest].getSimpleName, cause.getMessage), cause
  )
}

/**
  * An object encapsulating the state of this persistent entity.
  * Usage history facilitates graph plotting
  * @param household
  */
case class HouseholdState(household: Option[Household]) {

  def stock(item: InventoryItem): HouseholdState =
    copy(Option(household.get.copy(inventoryItems = household.get.inventoryItems :+ item)))

  /**
    * @param item consume from the InventoryItem with item.id
    * @param newRemainingFraction amount to consume as a fraction of the original absolute amount
    * @return
    */
  def consume(item: InventoryItem, newRemainingFraction: Float): HouseholdState = {
    household match {
      case Some(h) =>
        // create an updated state of the household
        new HouseholdState(Some(h.copy(inventoryItems =
          household.get.inventoryItems.map(inventoryItem => {
            if (item.id == inventoryItem.id) {
              // create copy of inventoryItem with reduced remainingFraction
              inventoryItem.copy(remainingFraction = newRemainingFraction)
            } else {
              inventoryItem
            }
          }))))
      case None =>
        throw new Error("should not occur")
    }
  }

  def remove(item: InventoryItem): HouseholdState = {
    household match {
      case Some(h) =>
        // create an updated state of the household
        new HouseholdState(Some(h.copy(inventoryItems =
          household.get.inventoryItems.filter(inventoryItem => item.id != inventoryItem.id))))
      case None =>
        throw new Error("should not occur")
    }
  }
}

object HouseholdState {
  implicit val format: Format[HouseholdState] = Json.format
  val newEntity = HouseholdState(None)
}

/*
Commands
 */

sealed trait HouseholdCommand[R] extends ReplyType[R]

final case class CreateHousehold(household: Household) extends HouseholdCommand[Done]
object CreateHousehold {
  implicit val format: Format[CreateHousehold] = Json.format
}

object GetHousehold extends HouseholdCommand[Option[Household]] {
  implicit val format: Format[GetHousehold.type] = JsonSerializer.emptySingletonFormat(GetHousehold)
}

final case class Consume(item: InventoryItem, newRemainingFraction: Float) extends HouseholdCommand[Done]
object Consume {
  implicit val format: Format[Consume] = Json.format
}

final case class Stock(item: InventoryItem) extends HouseholdCommand[Done]
object Stock extends HouseholdCommand[InventoryItem] {
  implicit val format: Format[Stock] = Json.format
}

/*
Events
 */

sealed trait HouseholdEvent

final case class HouseholdCreated(household: Household) extends HouseholdEvent
object HouseholdCreated {
  implicit val format: Format[HouseholdCreated] = Json.format
}

final case class InventoryItemPartiallyConsumed(item: InventoryItem, newRemainingFraction: Float) extends HouseholdEvent
object InventoryItemPartiallyConsumed {
  implicit val format: Format[InventoryItemPartiallyConsumed] = Json.format
}

final case class InventoryItemCompletelyConsumed(item: InventoryItem) extends HouseholdEvent
object InventoryItemCompletelyConsumed {
  implicit val format: Format[InventoryItemCompletelyConsumed] = Json.format
}

final case class InventoryItemStocked(newItem: InventoryItem) extends HouseholdEvent
object InventoryItemStocked {
  implicit val format: Format[InventoryItemStocked] = Json.format
}

/**
  * Akka serialization, used by both persistence and remoting, needs to have
  * serializers registered for every type serialized or deserialized. While it's
  * possible to use any serializer you want for Akka messages, out of the box
  * Lagom provides support for JSON, via this registry abstraction.
  *
  * The serializers are registered here, and then provided to Lagom in the
  * application loader.
  */
object HouseholdSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(

    // Value Objects
    JsonSerializer[Household],
    JsonSerializer[InventoryItem],

    // Commands
    JsonSerializer[CreateHousehold],
    JsonSerializer[GetHousehold.type],
    JsonSerializer[Consume],
    JsonSerializer[Stock],

    // Events
    JsonSerializer[HouseholdCreated],
    JsonSerializer[InventoryItemPartiallyConsumed],
    JsonSerializer[InventoryItemCompletelyConsumed],
    JsonSerializer[InventoryItemStocked]
  )
}
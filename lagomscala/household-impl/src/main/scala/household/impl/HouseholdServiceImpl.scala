package household.impl

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import lagomscala.household.api.{Household, HouseholdService, InventoryItem}
import java.util.UUID

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{NotFound, PolicyViolation, TransportErrorCode, TransportException}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

import scala.collection.immutable
import scala.collection.immutable.Seq
import scala.concurrent.ExecutionContext

/**
  * Created by srn7919 on 12.08.17.
  */
class HouseholdServiceImpl(registry: PersistentEntityRegistry, system: ActorSystem)(implicit ec: ExecutionContext, mat: Materializer) extends HouseholdService {

  override def create = ServiceCall { household =>
    val id = UUID.randomUUID()
    val newHousehold = household.copy(id = Some(id))
    refFor(id).ask(CreateHousehold(newHousehold)).map { _ => newHousehold }
  }

  override def getOne(householdId: UUID): ServiceCall[NotUsed, Household] = ServiceCall { _ =>
    refFor(householdId).ask(GetHousehold).map {
      case Some(household) => household
      case None => throw NotFound(s"Household with id ${householdId.toString} does not exist!")
    }
  }

  override def consume(householdId: UUID, itemId: UUID) = ServiceCall[Float, Done] { newRemainingFraction =>
    refFor(householdId).ask(Consume(itemId, newRemainingFraction))
  }

  override def stock(householdId: UUID): ServiceCall[InventoryItem, Done] = ServiceCall { item =>
    refFor(householdId).ask(Stock(item))
  }

  override def inventory(householdId: UUID) = ServiceCall[NotUsed, Seq[InventoryItem]] { _ =>
    refFor(householdId).ask(GetInventoryItems)
  }

  private def refFor(householdId: UUID) = registry.refFor[HouseholdEntity](householdId.toString)
}

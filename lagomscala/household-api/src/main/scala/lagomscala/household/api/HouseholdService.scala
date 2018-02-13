package lagomscala.household.api

import java.util.UUID

import akka.{Done, NotUsed}
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

import scala.collection.immutable.Seq


/**
  * Created by srn7919 on 12.08.17.
  */
trait HouseholdService extends Service {
  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"id": "123456-231231", "name": "Hauptstraße"}' http://localhost:9000/api/household
    */
  def create: ServiceCall[Household, Household]

  /**
    * Example: curl http://localhost:9000/api/household/:id
    */
  def getOne(householdId: UUID): ServiceCall[NotUsed, Household]

  def consume(householdId: UUID, itemId: UUID): ServiceCall[Float, Done]

  def stock(householdId: UUID): ServiceCall[InventoryItem, Done]

  def inventory(householdId: UUID): ServiceCall[NotUsed, Seq[InventoryItem]]

  override def descriptor: Descriptor = {
    import Service._
    named("household").withCalls(
      restCall(Method.POST, "/api/households", create _),
      pathCall("/api/households/:householdId", getOne _),
      restCall(Method.POST, "/api/households/:householdId/items", stock _),
      pathCall("/api/households/:householdId/items", inventory _),
      restCall(Method.PUT, "/api/households/:householdId/items/:itemId", consume _)
    ).withAutoAcl(true)
  }
}

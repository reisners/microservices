package household.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import lagomscala.household.api.HouseholdService
import play.api.libs.ws.ahc.AhcWSComponents

class HouseholdLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HouseholdApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HouseholdApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[HouseholdService]
  )
}

abstract class HouseholdApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[HouseholdService](wire[HouseholdServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = HouseholdSerializerRegistry

  // Register the lagomscala persistent entity
  persistentEntityRegistry.register(wire[HouseholdEntity])
}

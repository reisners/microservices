package household.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import java.util.UUID;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import household.api.Household;
import household.api.HouseholdService;

public interface HouseholdService extends Service {


	  /**
	   * Example: curl http://localhost:9000/api/household/:uuid
	   */
	  ServiceCall<NotUsed, Household> getHousehold(UUID uuid);

	  /**
	   * Example: curl -H "Content-Type: application/json" -X POST -d '{"uuid": "123456-231231"}' http://localhost:9000/api/household/
	   */
	  ServiceCall<Household, Done> putHousehold();
	  
	  @Override
	  default Descriptor descriptor() {
	    // @formatter:off
	    return named("household").withCalls(
	        pathCall("/api/household/:uuid",  this::getHousehold),
	        pathCall("/api/household", this::putHousehold)
	      ).withAutoAcl(true);
	    // @formatter:on
	  }

}

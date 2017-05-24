package household.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import akka.Done;
import akka.NotUsed;

public interface HouseholdService extends Service {


	  /**
	   * Example: curl http://localhost:9000/api/household/:id
	   */
	  ServiceCall<NotUsed, Household> getHousehold(String uuid);

	  /**
	   * Example: curl -H "Content-Type: application/json" -X POST -d '{"id": "123456-231231"}' http://localhost:9000/api/household/
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

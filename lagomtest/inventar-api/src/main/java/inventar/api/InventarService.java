package inventar.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import java.util.UUID;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import akka.Done;
import akka.NotUsed;

public interface InventarService extends Service {

	  /**
	   * Example: curl http://localhost:9000/api/inventar/:uuid
	   */
	  ServiceCall<NotUsed, Inventar> getInventar(UUID uuid);

	  /**
	   * Example: curl -H "Content-Type: application/json" -X POST -d '{"uuid": "123456-231231", "ean":
//	   * "1234556", "ursprungsmenge": {...}, "verbliebenerAnteil": 1.0, "gekauftAm": "2017-04-05", "haltbarBis": "2017-04-21" }' http://localhost:9000/api/inventar/
	   */
	  ServiceCall<Inventar, Done> putInventar();
	  
	  @Override
	  default Descriptor descriptor() {
	    // @formatter:off
	    return named("inventar").withCalls(
	        pathCall("/api/inventar/:uuid",  this::getInventar),
	        pathCall("/api/inventar", this::putInventar)
	      ).withAutoAcl(true);
	    // @formatter:on
	  }

}

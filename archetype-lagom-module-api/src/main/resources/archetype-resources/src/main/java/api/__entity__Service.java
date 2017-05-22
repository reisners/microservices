package ${package}.api;

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
import ${lower-case-name}.api.${entity};
import ${lower-case-name}.api.${entity}Service;

public interface ${entity}Service extends Service {


	  /**
	   * Example: curl http://localhost:9000/api/${lower-case-name}/:id
	   */
	  ServiceCall<NotUsed, ${entity}> get${entity}(String id);

	  /**
	   * Example: curl -H "Content-Type: application/json" -X POST -d '{"id": "123456-231231"}' http://localhost:9000/api/${lower-case-name}/
	   */
	  ServiceCall<${entity}, Done> put${entity}();
	  
	  @Override
	  default Descriptor descriptor() {
	    // @formatter:off
	    return named("${lower-case-name}").withCalls(
	        pathCall("/api/${lower-case-name}/:uuid",  this::get${entity}),
	        pathCall("/api/${lower-case-name}", this::put${entity})
	      ).withAutoAcl(true);
	    // @formatter:on
	  }

}

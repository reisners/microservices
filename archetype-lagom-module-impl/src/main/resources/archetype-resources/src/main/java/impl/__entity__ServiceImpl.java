package ${package}.impl;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import ${lower-case-name}.api.${entity};
import ${lower-case-name}.api.${entity}Service;
import ${lower-case-name}.impl.${entity}Command.Get${entity};
import ${lower-case-name}.impl.${entity}Command.Put${entity};

public class ${entity}ServiceImpl implements ${entity}Service {

	@Inject
	private PersistentEntityRegistry persistentEntityRegistry;

	@Inject
	public ${entity}ServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
		this.persistentEntityRegistry = persistentEntityRegistry;
		persistentEntityRegistry.register(${entity}Entity.class);
	}

	@Override
	public ServiceCall<NotUsed, ${entity}> get${entity}(String ean) {
		return request -> {
			// Look up the hello world entity for the given ID.
			PersistentEntityRef<${entity}Command> ref = persistentEntityRegistry.refFor(${entity}Entity.class, ean);
			// Ask the entity the Read${entity} command.
			return ref
					.ask(new Get${entity}())
					.thenApply(maybe${entity} -> {
						if (maybe${lower-case-name}.isPresent()) {
							return maybe${lower-case-name}.get();
						} else {
							throw new NotFound("${entity} " + ean + " not found");
						}
					});
		};
	}

	@Override
	public ServiceCall<${entity}, Done> put${entity}() {
		return ${entity} -> {
			PersistentEntityRef<${entity}Command> ref = persistentEntityRegistry.refFor(${entity}Entity.class, ${lower-case-name}.getEAN());
			return ref.ask(new Put${entity}(${entity}));
		};
	}

}

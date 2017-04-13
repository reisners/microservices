package inventar.impl;

import java.util.UUID;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import inventar.api.Inventar;
import inventar.api.InventarService;
import inventar.impl.InventarCommand.GetInventar;
import inventar.impl.InventarCommand.PutInventar;

public class InventarServiceImpl implements InventarService {

	@Inject
	private PersistentEntityRegistry persistentEntityRegistry;

	@Inject
	public InventarServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
		this.persistentEntityRegistry = persistentEntityRegistry;
		persistentEntityRegistry.register(InventarEntity.class);
	}

	@Override
	public ServiceCall<NotUsed, Inventar> getInventar(UUID uuid) {
		return request -> {
			// Look up the hello world entity for the given ID.
			PersistentEntityRef<InventarCommand> ref = persistentEntityRegistry.refFor(InventarEntity.class, uuid.toString());
			// Ask the entity the ReadInventar command.
			return ref
					.ask(new GetInventar())
					.thenApply(maybeInventar -> {
						if (maybeInventar.isPresent()) {
							return maybeInventar.get();
						} else {
							throw new NotFound("Inventar " + uuid + " not found");
						}
					});
		};
	}

	@Override
	public ServiceCall<Inventar, Done> putInventar() {
		return Inventar -> {
			PersistentEntityRef<InventarCommand> ref = persistentEntityRegistry.refFor(InventarEntity.class, Inventar.uuid().toString());
			return ref.ask(new PutInventar(Inventar));
		};
	}

}

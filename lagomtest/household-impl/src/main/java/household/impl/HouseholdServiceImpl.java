package household.impl;

import java.util.UUID;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import household.api.Household;
import household.api.HouseholdService;
import household.impl.HouseholdCommand.GetHousehold;
import household.impl.HouseholdCommand.PutHousehold;

public class HouseholdServiceImpl implements HouseholdService {

	@Inject
	private PersistentEntityRegistry persistentEntityRegistry;

	@Inject
	public HouseholdServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
		this.persistentEntityRegistry = persistentEntityRegistry;
		persistentEntityRegistry.register(HouseholdEntity.class);
	}

	@Override
	public ServiceCall<NotUsed, Household> getHousehold(UUID householdId) {
		return request -> {
			// Look up the hello world entity for the given ID.
			PersistentEntityRef<HouseholdCommand> ref = persistentEntityRegistry.refFor(HouseholdEntity.class, householdId.toString());
			// Ask the entity the ReadHousehold command.
			return ref
					.ask(new GetHousehold())
					.thenApply(maybeHousehold -> {
						if (maybeHousehold.isPresent()) {
							return maybeHousehold.get();
						} else {
							throw new NotFound("Household " + householdId + " not found");
						}
					});
		};
	}

	@Override
	public ServiceCall<Household, Done> putHousehold() {
		return household -> {
			PersistentEntityRef<HouseholdCommand> ref = persistentEntityRegistry.refFor(HouseholdEntity.class, household.id().toString());
			return ref.ask(new PutHousehold(household));
		};
	}

}

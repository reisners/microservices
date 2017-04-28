package household.impl.impl;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import household.api.Household;
import household.impl.HouseholdCommand.GetHousehold;
import household.impl.HouseholdCommand.PutHousehold;
import household.impl.HouseholdEvent.HouseholdCreated;

public class HouseholdEntity extends PersistentEntity<HouseholdCommand, HouseholdEvent, HouseholdState> {

	@Override
	public Behavior initialBehavior(Optional<HouseholdState> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(HouseholdState.empty());
		
		b.setReadOnlyCommandHandler(Gethousehold.class, this::getHousehold);

        // maybe do some validation? Eg, check that UUID of item matches entity UUID...
        b.setCommandHandler(Puthousehold.class, (create, ctx) ->
                ctx.thenPersist(new HouseholdCreated(create.getHousehold()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(HouseholdCreated.class, evt -> created(HouseholdState.create(evt.getHousehold())));
        
        return b.build();
	}
	
	private Behavior created(HouseholdState state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		b.setReadOnlyCommandHandler(Gethousehold.class, this::getHousehold);
		
		return b.build();
	}

	private void getHousehold(GetHousehold get, ReadOnlyCommandContext<Optional<Household>> ctx) {
		ctx.reply(state().getHousehold());
	}

}

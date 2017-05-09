package household.impl.impl;

import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import household.api.Household;
import household.impl.impl.HouseholdCommand.GetHousehold;
import household.impl.impl.HouseholdCommand.PutHousehold;
import household.impl.impl.HouseholdEvent.HouseholdCreated;

public class HouseholdEntity extends PersistentEntity<HouseholdCommand, HouseholdEvent, HouseholdState> {

	@Override
	public Behavior initialBehavior(Optional<HouseholdState> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(HouseholdState.empty());
		
		b.setReadOnlyCommandHandler(GetHousehold.class, this::getHousehold);

        // maybe do some validation? Eg, check that UUID of item matches entity UUID...
        b.setCommandHandler(PutHousehold.class, (create, ctx) ->
                ctx.thenPersist(new HouseholdCreated(create.getHousehold()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(HouseholdCreated.class, evt -> created(HouseholdState.create(evt.getHousehold())));
        
        return b.build();
	}
	
	private Behavior created(HouseholdState state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		b.setReadOnlyCommandHandler(GetHousehold.class, this::getHousehold);
		
		return b.build();
	}

	private void getHousehold(GetHousehold get, ReadOnlyCommandContext<Optional<Household>> ctx) {
		ctx.reply(state().getHousehold());
	}

}

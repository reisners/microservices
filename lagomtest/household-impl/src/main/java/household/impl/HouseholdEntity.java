package household.impl;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import household.api.Household;
import household.impl.HouseholdCommand.AddInventoryItem;
import household.impl.HouseholdCommand.GetHousehold;
import household.impl.HouseholdCommand.PutHousehold;
import household.impl.HouseholdCommand.RemoveInventoryItem;
import household.impl.HouseholdEvent.HouseholdCreated;
import household.impl.HouseholdEvent.InventoryItemAdded;
import household.impl.HouseholdEvent.InventoryItemRemoved;

public class HouseholdEntity extends PersistentEntity<HouseholdCommand, HouseholdEvent, HouseholdState> {

	@Override
	public Behavior initialBehavior(Optional<HouseholdState> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(HouseholdState.empty());
		
		b.setReadOnlyCommandHandler(GetHousehold.class, this::getHousehold);

        // maybe do some validation? Eg, check that id of item matches entity id...
        b.setCommandHandler(PutHousehold.class, (create, ctx) ->
                ctx.thenPersist(new HouseholdCreated(create.getHousehold()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(HouseholdCreated.class, evt -> created(HouseholdState.create(evt.getHousehold())));
        
        return b.build();
	}
	
	private Behavior created(HouseholdState state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		// Command handlers
		b.setReadOnlyCommandHandler(GetHousehold.class, this::getHousehold);
		b.setCommandHandler(AddInventoryItem.class, (add, ctx) -> ctx.thenPersist(new InventoryItemAdded(add.getHouseholdId(), add.getInventoryItem())));
		b.setCommandHandler(RemoveInventoryItem.class,
				(remove, ctx) -> ctx.thenPersist(new InventoryItemRemoved(remove.getHouseholdId(), remove.getInventoryItemId())));

		// Event handlers
		b.setEventHandler(InventoryItemAdded.class, added -> state().addInventoryItem(added.getInventoryItem()));
		
		return b.build();
	}

	private void getHousehold(GetHousehold get, ReadOnlyCommandContext<Optional<Household>> ctx) {
		ctx.reply(state().getHousehold());
	}
}

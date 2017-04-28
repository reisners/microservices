package inventar.impl;

import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import inventar.api.ActualInventory;
import inventar.impl.InventarCommand.GetInventar;
import inventar.impl.InventarCommand.PutInventar;
import inventar.impl.InventarEvent.InventarCreated;

public class InventarEntity extends PersistentEntity<InventarCommand, InventarEvent, InventarState> {

	@Override
	public Behavior initialBehavior(Optional<InventarState> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(InventarState.empty());
		
		b.setReadOnlyCommandHandler(GetInventar.class, this::getInventar);

        // maybe do some validation? Eg, check that UUID of item matches entity UUID...
        b.setCommandHandler(PutInventar.class, (create, ctx) ->
                ctx.thenPersist(new InventarCreated(create.getInventar()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(InventarCreated.class, evt -> created(InventarState.create(evt.getInventar())));
        
        return b.build();
	}
	
	private Behavior created(InventarState state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		b.setReadOnlyCommandHandler(GetInventar.class, this::getInventar);
		
		return b.build();
	}

	private void getInventar(GetInventar get, ReadOnlyCommandContext<Optional<ActualInventory>> ctx) {
		ctx.reply(state().getInventar());
	}

}

package ${package}.impl;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import ${lower-case-name}.api.${entity};
import ${lower-case-name}.impl.${entity}Command.Get${entity};
import ${lower-case-name}.impl.${entity}Command.Put${entity};
import ${lower-case-name}.impl.${entity}Event.${entity}Created;

public class ${entity}Entity extends PersistentEntity<${entity}Command, ${entity}Event, ${entity}State> {

	@Override
	public Behavior initialBehavior(Optional<${entity}State> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(${entity}State.empty());
		
		b.setReadOnlyCommandHandler(Get${entity}.class, this::get${entity});

        // maybe do some validation? Eg, check that UUID of item matches entity UUID...
        b.setCommandHandler(Put${entity}.class, (create, ctx) ->
                ctx.thenPersist(new ${entity}Created(create.get${entity}()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(${entity}Created.class, evt -> created(${entity}State.create(evt.get${entity}())));
        
        return b.build();
	}
	
	private Behavior created(${entity}State state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		b.setReadOnlyCommandHandler(Get${entity}.class, this::get${entity});
		
		return b.build();
	}

	private void get${entity}(Get${entity} get, ReadOnlyCommandContext<Optional<${entity}>> ctx) {
		ctx.reply(state().get${entity}());
	}

}

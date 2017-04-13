package de.ryznr.product.impl;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import de.ryznr.product.api.Product;
import de.ryznr.product.impl.ProductCommand.GetProduct;
import de.ryznr.product.impl.ProductCommand.PutProduct;
import de.ryznr.product.impl.ProductEvent.ProductCreated;

public class ProductEntity extends PersistentEntity<ProductCommand, ProductEvent, ProductState> {

	@Override
	public Behavior initialBehavior(Optional<ProductState> snapshotState) {
		
		return snapshotState.map(state -> created(state)).orElse(empty());
	}
	
	private Behavior empty() {
		BehaviorBuilder b = newBehaviorBuilder(ProductState.empty());
		
		b.setReadOnlyCommandHandler(GetProduct.class, this::getProduct);

        // maybe do some validation? Eg, check that UUID of item matches entity UUID...
        b.setCommandHandler(PutProduct.class, (create, ctx) ->
                ctx.thenPersist(new ProductCreated(create.getProduct()), evt -> ctx.reply(Done.getInstance()))
        );
        b.setEventHandlerChangingBehavior(ProductCreated.class, evt -> created(ProductState.create(evt.getProduct())));
        
        return b.build();
	}
	
	private Behavior created(ProductState state) {
		BehaviorBuilder b = newBehaviorBuilder(state);

		b.setReadOnlyCommandHandler(GetProduct.class, this::getProduct);
		
		return b.build();
	}

	private void getProduct(GetProduct get, ReadOnlyCommandContext<Optional<Product>> ctx) {
		ctx.reply(state().getProduct());
	}

}

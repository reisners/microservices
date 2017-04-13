package product.impl;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import product.api.Product;
import product.api.ProductService;
import product.impl.ProductCommand.GetProduct;
import product.impl.ProductCommand.PutProduct;

public class ProductServiceImpl implements ProductService {

	@Inject
	private PersistentEntityRegistry persistentEntityRegistry;

	@Inject
	public ProductServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
		this.persistentEntityRegistry = persistentEntityRegistry;
		persistentEntityRegistry.register(ProductEntity.class);
	}

	@Override
	public ServiceCall<NotUsed, Product> getProduct(String ean) {
		return request -> {
			// Look up the hello world entity for the given ID.
			PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductEntity.class, ean);
			// Ask the entity the ReadProduct command.
			return ref
					.ask(new GetProduct())
					.thenApply(maybeProduct -> {
						if (maybeProduct.isPresent()) {
							return maybeProduct.get();
						} else {
							throw new NotFound("Product " + ean + " not found");
						}
					});
		};
	}

	@Override
	public ServiceCall<Product, Done> putProduct() {
		return product -> {
			PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductEntity.class, product.getEAN());
			return ref.ask(new PutProduct(product));
		};
	}

}

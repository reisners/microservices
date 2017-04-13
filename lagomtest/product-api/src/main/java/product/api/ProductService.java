package de.ryznr.product.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import akka.Done;
import akka.NotUsed;
import de.ryznr.product.api.Product;

public interface ProductService extends Service {

	  /**
	   * Example: curl http://localhost:9000/api/product/:EAN
	   */
	  ServiceCall<NotUsed, Product> getProduct(String ean);

	  /**
	   * Example: curl -H "Content-Type: application/json" -X POST -d '{"name":
	   * "TV Dinner Chicken"}' http://localhost:9000/api/product/:EAN
	   */
	  ServiceCall<Product, Done> putProduct();
	  
	  @Override
	  default Descriptor descriptor() {
	    // @formatter:off
	    return named("product").withCalls(
	        pathCall("/api/product/:ean",  this::getProduct),
	        pathCall("/api/product", this::putProduct)
	      ).withAutoAcl(true);
	    // @formatter:on
	  }

}

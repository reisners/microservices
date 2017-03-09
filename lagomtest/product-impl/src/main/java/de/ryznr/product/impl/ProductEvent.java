package de.ryznr.product.impl;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import de.ryznr.product.api.Product;

public interface ProductEvent extends Jsonable {
	
	/**
	 * An event that represents a newly created product.
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class ProductCreated implements ProductEvent {
		public final Product product;

		public ProductCreated(Product product) {
			this.product = product;
		}

		public Product getProduct() {
			return product;
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, false);
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, false);
		}

		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
		}
	}
}

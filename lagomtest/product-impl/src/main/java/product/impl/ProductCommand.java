package de.ryznr.product.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;
import de.ryznr.product.api.Product;

public interface ProductCommand extends Jsonable {

	/**
	 * A command to retrieve a product by its EAN
	 * <p>
	 * The reply is the product
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class GetProduct implements ProductCommand, PersistentEntity.ReplyType<Optional<Product>> {
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

	/**
	 * A command to add a new product
	 * <p>
	 * It has a reply type of {@link akka.Done}, which is sent back to the
	 * caller when all the events emitted by this command are successfully
	 * persisted.
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class PutProduct implements ProductCommand, PersistentEntity.ReplyType<Done> {
		private Product product;

		public PutProduct(Product product) {
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

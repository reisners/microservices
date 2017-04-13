package de.ryznr.product.impl;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.ryznr.product.api.Product;

public class ProductState {
	private Optional<Product> product;

	public ProductState(Optional<Product> product) {
		this.product = product;
	}
	
	public static ProductState empty() {
		return new ProductState(Optional.empty());
	}
	
	public static ProductState create(Product product) {
		return new ProductState(Optional.of(product));
	}

	public Optional<Product> getProduct() {
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

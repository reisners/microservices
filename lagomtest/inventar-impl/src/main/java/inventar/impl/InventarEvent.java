package inventar.impl;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import inventar.api.Inventar;

public interface InventarEvent extends Jsonable {
	
	/**
	 * An event that represents a newly created Inventar.
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class InventarCreated implements InventarEvent {
		public final Inventar Inventar;

		public InventarCreated(Inventar Inventar) {
			this.Inventar = Inventar;
		}

		public Inventar getInventar() {
			return Inventar;
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

package household.impl.impl;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import household.api.Household;

public interface HouseholdEvent extends Jsonable {
	
	/**
	 * An event that represents a newly created household.
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class HouseholdCreated implements HouseholdEvent {
		public final Household Household;

		public HouseholdCreated(Household Household) {
			this.Household = Household;
		}

		public Household getHousehold() {
			return Household;
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

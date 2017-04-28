package household.impl.impl;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HouseholdState {
	private Optional<Household> Household;

	public HouseholdState(Optional<Household> Household) {
		this.Household = Household;
	}
	
	public static HouseholdState empty() {
		return new HouseholdState(Optional.empty());
	}
	
	public static HouseholdState create(Household Household) {
		return new HouseholdState(Optional.of(Household));
	}

	public Optional<Household> getHousehold() {
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

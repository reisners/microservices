package inventar.impl;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import inventar.api.Inventar;

public class InventarState {
	private Optional<Inventar> Inventar;

	public InventarState(Optional<Inventar> Inventar) {
		this.Inventar = Inventar;
	}
	
	public static InventarState empty() {
		return new InventarState(Optional.empty());
	}
	
	public static InventarState create(Inventar Inventar) {
		return new InventarState(Optional.of(Inventar));
	}

	public Optional<Inventar> getInventar() {
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

package valueobjects;

import java.net.URI;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@JsonDeserialize
public class Amount {
	private final Optional<URI> unit;
	private final float value;
	
	public Amount(URI unit, float value) {
		this.unit = Optional.of(unit);
		this.value = value;
	}

	public Amount(float value) {
		this.unit = Optional.empty();
		this.value = value;
	}
	
	public Optional<URI> unit() { return unit; };
	public float value() {
		return value;
	}
}

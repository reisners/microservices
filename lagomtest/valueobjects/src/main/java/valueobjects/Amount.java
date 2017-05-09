package valueobjects;

import java.net.URI;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize
public interface Amount {
	default Optional<URI> unit() { return Optional.empty(); };
	float value();
}

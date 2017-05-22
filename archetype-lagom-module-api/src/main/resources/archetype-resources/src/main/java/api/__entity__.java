package ${package}.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize
/**
 * Interface specifying the properties of entity ${entity}
 */
public interface ${entity} {

	/**
	 * @return this instance's unique id
	 */
	String id();

}

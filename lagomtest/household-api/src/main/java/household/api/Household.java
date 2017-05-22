package household.api;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize
/**
 * Interface specifying the properties of entity Household
 */
public interface Household {

	/**
	 * @return this instance's unique id
	 */
	String id();
	
	/**
	 * @return the {@code Household}'s display name
	 */
	String name();

	/**
	 * @return the {@code Household}'s inventory items
	 */
	Set<InventoryItem> inventoryItems();
}

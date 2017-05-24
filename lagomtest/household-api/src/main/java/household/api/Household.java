package household.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@JsonDeserialize
public class Household {

	private String id;
	private String name;
	private List<InventoryItem> inventoryItems = new ArrayList<>();
	
	/**
	 * @return this instance's unique id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the {@code Household}'s display name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return this {@code Household}'s list of {@code InventoryItem}s
	 */
	public List<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}
}

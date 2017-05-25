package household.api;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@JsonDeserialize
public class Household {

	private final String id;
	private final String name;
	private final List<InventoryItem> inventoryItems;
	
	@JsonCreator
	public Household(String id, String name, List<InventoryItem> inventoryItems) {
		this.id = id;
		this.name = name;
		this.inventoryItems = inventoryItems;
	}

	/**
	 * @return this instance's unique id
	 */
	public String id() {
		return id;
	}

	/**
	 * @return the {@code Household}'s display name
	 */
	public String name() {
		return name;
	}

	/**
	 * @return this {@code Household}'s list of {@code InventoryItem}s
	 */
	public List<InventoryItem> inventoryItems() {
		return inventoryItems;
	}
}

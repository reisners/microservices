package household.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import household.api.Household;
import household.api.InventoryItem;

public class HouseholdState {
	private Optional<Household> household;

	public HouseholdState(Optional<Household> household) {
		this.household = household;
	}
	
	public static HouseholdState empty() {
		return new HouseholdState(Optional.empty());
	}
	
	public static HouseholdState create(Household household) {
		return new HouseholdState(Optional.of(household));
	}

	public Optional<Household> getHousehold() {
		return household;
	}
	
	public HouseholdState addInventoryItem(InventoryItem inventoryItem) {
		return new HouseholdState(
				household.map(h -> {
					List<InventoryItem> h1 = new LinkedList<InventoryItem>(h.inventoryItems());
					h1.add(inventoryItem);
					return new Household(h.id(), h.name(), h1);
				}));
	}
	
	public HouseholdState removeInventoryItem(String inventoryItemId) {
		return new HouseholdState(
				household.map(h -> {
					List<InventoryItem> i1 = h.inventoryItems().stream()
							.filter(item -> !item.id().equals(inventoryItemId)).collect(Collectors.toList());
					return new Household(h.id(), h.name(), i1);
				}));
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

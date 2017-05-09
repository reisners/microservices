package household.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import valueobjects.Amount;
import valueobjects.ImmutableAmount;

@Value.Immutable
@JsonDeserialize
public interface InventoryItem {

	/**
	 * @return this instance's unique id
	 */
	UUID uuid();
	/**
	 * @return id of the {@code Household} that this instance belongs to
	 */
	UUID householdId();
	/**
	 * @return id of the {@code Product} this is an instance of
	 */
	UUID productId();
	/**
	 * @return the original amount (for products with an EAN the unit should be piece specifying the number of items). Defaults to 1 piece.
	 */
	@Value.Default()
	default
	Amount originalAmount() {
		return ImmutableAmount.builder().value(1f).build();
	}
	/**
	 * @return the fraction of the original amount that actually exists (i.e. has not been consumed or perished)
	 */
	float remainingFraction();
	/**
	 * @return the timestamp when this instance was added to the inventory
	 */
	Date obtained();
	/**
	 * @return the best before date as printed on the packaging of the item
	 */
	Date bestBefore();

}

package household.api;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import valueobjects.Amount;
import valueobjects.ImmutableAmount;

@Immutable
@JsonDeserialize
public class InventoryItem {
	private String id;
	
	private String householdId;
	
	private String productId;
	
	private Amount originalAmount = Amount.builder().withUnit(Amount.Unit.piece).withValue(1f);
	
	/**
	 * @return this instance's id (unique within each household)
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return id of the {@code Household} that this instance belongs to
	 */
	public String getHouseholdId() {
		return householdId;
	}
	
	/**
	 * @return id of the {@code Product} that is inventorized
	 */
	public String getProductId() {
		return productId;
	}

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

package household.api;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import valueobjects.Amount;

@Immutable
@JsonDeserialize
public class InventoryItem {
	private final String id;
	private final String householdId;
	private final String productId;
	private final Amount originalAmount;
	private final float remainingFraction;
	private final LocalDate obtained;
	private final LocalDate bestBefore;

	@JsonCreator
	public InventoryItem(String id, String householdId, String productId, Amount originalAmount,
			float remainingFraction, LocalDate obtained, LocalDate bestBefore) {
		this.id = id;
		this.householdId = householdId;
		this.productId = productId;
		this.originalAmount = originalAmount;
		this.remainingFraction = remainingFraction;
		this.obtained = obtained;
		this.bestBefore = bestBefore;
	}

	/**
	 * @return this instance's id (unique within each household)
	 */
	public String id() {
		return id;
	}

	/**
	 * @return id of the {@code Household} that this instance belongs to
	 */
	public String householdId() {
		return householdId;
	}
	
	/**
	 * @return id of the {@code Product} that is inventorized
	 */
	public String productId() {
		return productId;
	}

	/**
	 * @return the original amount (for products with an EAN the unit should be piece specifying the number of items). Defaults to 1 piece.
	 */
	Amount originalAmount() {
		return originalAmount;
	}
	/**
	 * @return the fraction of the original amount that actually exists (i.e. has not been consumed or perished)
	 */
	float remainingFraction() {
		return remainingFraction;
	}
	/**
	 * @return the timestamp when this instance was added to the inventory
	 */
	LocalDate obtained() {
		return obtained ;
	}
	/**
	 * @return the best before date as printed on the packaging of the item
	 */
	LocalDate bestBefore() {
		return bestBefore ;
	}

}

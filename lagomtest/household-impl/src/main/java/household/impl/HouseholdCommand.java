package household.impl;

import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;
import household.api.Household;
import household.api.InventoryItem;

public interface HouseholdCommand extends Jsonable {

	/**
	 * A command to retrieve a Household by its EAN
	 * <p>
	 * The reply is the Household
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class GetHousehold implements HouseholdCommand, PersistentEntity.ReplyType<Optional<Household>> {
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

	/**
	 * A command to add a new Household
	 * <p>
	 * It has a reply type of {@link akka.Done}, which is sent back to the
	 * caller when all the events emitted by this command are successfully
	 * persisted.
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class PutHousehold implements HouseholdCommand, PersistentEntity.ReplyType<Done> {
		private Household household;

		public PutHousehold(Household household) {
			this.household = household;
		}

		public Household getHousehold() {
			return household;
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

	/**
	 * A command to add an inventory item to the household
	 * <p>
	 * It has a reply type of {@link akka.Done}, which is sent back to the
	 * caller when all the events emitted by this command are successfully
	 * persisted.
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class AddInventoryItem implements HouseholdCommand, PersistentEntity.ReplyType<Done> {
		private String householdId;
		private InventoryItem inventoryItem;

		public AddInventoryItem(String householdId, InventoryItem inventoryItem) {
			this.householdId = householdId;
			this.inventoryItem = inventoryItem;
		}

		public String getHouseholdId() {
			return householdId;
		}

		public InventoryItem getInventoryItem() {
			return inventoryItem;
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

	/**
	 * A command to remove an inventory item from the household
	 * <p>
	 * It has a reply type of {@link akka.Done}, which is sent back to the
	 * caller when all the events emitted by this command are successfully
	 * persisted.
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class RemoveInventoryItem implements HouseholdCommand, PersistentEntity.ReplyType<Done> {
		private String householdId;
		private String inventoryItemId;

		public RemoveInventoryItem(String householdId, String inventoryItemId) {
			this.householdId = householdId;
			this.inventoryItemId = inventoryItemId;
		}

		public String getHouseholdId() {
			return householdId;
		}

		public String getInventoryItemId() {
			return inventoryItemId;
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
}

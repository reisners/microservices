package inventar.impl;

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
import inventar.api.Inventar;

public interface InventarCommand extends Jsonable {

	/**
	 * A command to retrieve a Inventar by its EAN
	 * <p>
	 * The reply is the Inventar
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class GetInventar implements InventarCommand, PersistentEntity.ReplyType<Optional<Inventar>> {
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
	 * A command to add a new Inventar
	 * <p>
	 * It has a reply type of {@link akka.Done}, which is sent back to the
	 * caller when all the events emitted by this command are successfully
	 * persisted.
	 *
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class PutInventar implements InventarCommand, PersistentEntity.ReplyType<Done> {
		private Inventar Inventar;

		public PutInventar(Inventar Inventar) {
			this.Inventar = Inventar;
		}

		public Inventar getInventar() {
			return Inventar;
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

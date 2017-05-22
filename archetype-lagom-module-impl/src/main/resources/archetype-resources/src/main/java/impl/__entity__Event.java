package ${package}.impl;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import ${lower-case-name}.api.${entity};

public interface ${entity}Event extends Jsonable {
	
	/**
	 * An event that represents a newly created ${lower-case-name}.
	 */
	@SuppressWarnings("serial")
	@Immutable
	@JsonDeserialize
	public final class ${entity}Created implements ${entity}Event {
		public final ${entity} ${lower-case-name};

		public ${entity}Created(${entity} ${lower-case-name}) {
			this.${lower-case-name} = ${lower-case-name};
		}

		public ${entity} get${entity}() {
			return ${lower-case-name};
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

package ${package}.impl;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ${entity}State {
	private Optional<${entity}> ${entity};

	public ${entity}State(Optional<${entity}> ${entity}) {
		this.${entity} = ${entity};
	}
	
	public static ${entity}State empty() {
		return new ${entity}State(Optional.empty());
	}
	
	public static ${entity}State create(${entity} ${entity}) {
		return new ${entity}State(Optional.of(${entity}));
	}

	public Optional<${entity}> get${entity}() {
		return ${entity};
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

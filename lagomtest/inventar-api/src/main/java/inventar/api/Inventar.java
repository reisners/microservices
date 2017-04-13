package inventar.api;

import java.util.Date;
import java.util.UUID;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import valueobjects.EAN;
import valueobjects.Menge;

@Value.Immutable
@JsonDeserialize
public interface Inventar {

	UUID uuid();
	EAN vonEAN();
	Menge ursprungsmenge();
	float verbliebenerAnteil();
	Date gekauftAm();
	Date haltbarBis();

}

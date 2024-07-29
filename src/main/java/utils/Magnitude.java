package utils;

import javax.persistence.Embeddable;

@Embeddable
public class Magnitude {
	private Double value;
	private String unit;

	// Constructors
	public Magnitude() {
	}

	public Magnitude(Double value, String unit) {
		this.value = value;
		this.unit = unit;
	}

	// Getters
	public Double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	// Setters
	public void setValue(Double value) {
		this.value = value;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}

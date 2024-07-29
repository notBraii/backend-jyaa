package models;

import utils.Magnitude;

import javax.persistence.*;

@Entity
public class Ingredient {
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private ResourceTag resourceTag;
	
	@Embedded
	private Magnitude magnitude;
		
	// Constructors
	public Ingredient() {
	}
	
	public Ingredient(ResourceTag resourceTag, Magnitude magnitude) {
		this.setResource(resourceTag);
		this.setMagnitude(magnitude);
	}
	// Getters
	public Long getId() {
		return id;
	}
	public ResourceTag getResource() {
		return resourceTag;
	}
	
	public Magnitude getMagnitude() {
		return magnitude;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setResource(ResourceTag resourceTag) {
		this.resourceTag = resourceTag;
	}

	public void setMagnitude(Magnitude magnitude) {
		this.magnitude = magnitude;
	}
}

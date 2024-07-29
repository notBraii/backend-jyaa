package models;

import javax.persistence.*;

@Entity
public class ProducingFamily {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String location;

	// Constructors
	public ProducingFamily() {
	}
	
	public ProducingFamily(String name, String location) {
		this.name = name;
		this.location = location;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

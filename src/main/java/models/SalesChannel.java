package models;

import javax.persistence.*;

@Entity
public class SalesChannel {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String name;

	// Constructors
	public SalesChannel() {
	}
	
	public SalesChannel(String name) {
		this.name = name;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
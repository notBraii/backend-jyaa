package models;

import javax.persistence.*;

@Entity
public class ResourceTag {
	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;

	// Constructors
	public ResourceTag() {	
	}
	
	public ResourceTag(String name) {
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
	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

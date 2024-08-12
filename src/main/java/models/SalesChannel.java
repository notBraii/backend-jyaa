package models;

import javax.persistence.*;

@Entity
public class SalesChannel {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String name;

	private String address;
	
	private String contact;
	
	// Constructors
	public SalesChannel() {
	}
	
	public SalesChannel(String name, String address, String contact) {
		this.name = name;
		this.address = address;
		this.contact = contact;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}

	public String getContact() {
		return contact;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
}
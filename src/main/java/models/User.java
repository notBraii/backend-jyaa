package models;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	private String fullname;
	
	@Column(unique = true)
	private String username;
	
	private String pass;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	// Constructors
	public User() {
	}

	public User(String userName, String password, String fullName) {
		this.fullname = fullName;
		this.username = userName;
		this.pass = password;
		this.role = Role.NORMAL ;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public Role getRole() {
		return role;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setRole(Role role) {
			this.role = role;
	}
}
package models;

import java.util.List;

import javax.persistence.*;

@Entity
public class Recipe {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Ingredient> ingredients;

	// Constructors
	public Recipe() {
	}
	
	public Recipe(String name, List<Ingredient> ingredients, Category category) {
		this.name = name;
		this.ingredients = ingredients;
		this.category = category;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public Category getCategory() {
		return category;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
}
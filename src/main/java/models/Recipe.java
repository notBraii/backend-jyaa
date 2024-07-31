package models;

import java.util.List;

import javax.persistence.*;

@Entity
public class Recipe {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	@ManyToOne
	private Category category;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Ingredient> ingredients;
	
	private String steps;
	
	// Constructors
	public Recipe() {
	}
	
	public Recipe(String name, String description, Category category, List<Ingredient> ingredients, String steps) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.ingredients = ingredients;
		this.steps = steps;
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
	
	public String getDescription() {
		return description;
	}
	
	public String getSteps() {
		return steps;
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setSteps(String steps) {
		this.steps = steps;
	}
}
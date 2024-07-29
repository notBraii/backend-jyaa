package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Product {
	@Id @GeneratedValue
	private Long id;
	
	private Integer batch;
	private String name;
	private Date productionDate;
	private Integer quantity;
	
	@ManyToOne
	private Category category;
	
	@ElementCollection
	private List<String> observations;
	
	@OneToOne
	private Recipe recipe;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<ProductResource> productResources;

	// Constructors
	public Product() {	
	}
	
	public Product(Integer batch, String name, Date productionDate, Integer quantity, List<String> observations, Recipe recipe, List<ProductResource> productResources, Category category) {
		this.batch = batch;
		this.name = name;
		this.productionDate = productionDate;
		this.quantity = quantity;
		this.observations = observations;
		this.recipe = recipe;
		this.productResources = productResources;
		this.category = category;
	}

	// Getters
	public Long getId() {
		return id;
	}
	
	public Integer getBatch() {
		return batch;
	}

	public String getName() {
		return name;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public List<String> getObservations() {
		return new ArrayList<>(observations);
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public List<ProductResource> getProductresources() {
		return productResources;
	}
	
	public Category getCategory() {
		return category;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setObservations(List<String> observations) {
		this.observations = observations;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public void setProductResources(List<ProductResource> productResources) {
		this.productResources = productResources;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

}

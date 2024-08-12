package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductGroup {
	@Id @GeneratedValue
	private Long id;

	@Column(unique=true)
	private String name;
	private Integer stock;
	
	@ManyToOne
	private Category category;
	
	
	public ProductGroup() {
	}
	
	public ProductGroup(String name, Integer stock, Category category) {
		this.name = name;
		this.stock = stock;
		this.category = category;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
}

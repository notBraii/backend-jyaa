package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Product {
	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String batch;
	private String name;
	private Date productionDate;
	private Integer quantity;
	private Double price;
	private String imageURL;
	@ManyToOne
	private ProductGroup productGroup;
	
	@ElementCollection
	private List<String> observations = new ArrayList<>();
	
	@OneToOne
	private Recipe recipe;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<ProductResource> productResources;

	// Constructors
	public Product() {	
	}
	
	public Product(String batch, String name, Date productionDate, Integer quantity, List<String> observations, Recipe recipe, List<ProductResource> productResources, ProductGroup productGroup, String imageURL) {
		this.batch = batch;
		this.name = name;
		this.productionDate = productionDate;
		this.quantity = quantity;
		this.observations = observations;
		this.recipe = recipe;
		this.productResources = productResources;
		this.productGroup = productGroup;
		this.imageURL = imageURL;
		this.price = calculatePrice();
	}
	 // Método para calcular el precio en base a los ProductResources
    private Double calculatePrice() {
        double totalPrice = 0.0;
        
        for (ProductResource pr : productResources) {
            StockResource stockResource = pr.getStockResource();
            if (stockResource != null) {
                double stockPrice = stockResource.getPrice();
                double usedQuantity = pr.getUsedQuantity().getValue();
                //String usedUnit = pr.getUsedQuantity().getUnit();
                
                
                totalPrice += stockPrice * usedQuantity;
            }
        }
        
        return totalPrice;
    }
	// Getters
	public Long getId() {
		return id;
	}
	
	public String getBatch() {
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

	public Double getPrice() {
		return price;
	}

	public List<String> getObservations() {
		return new ArrayList<>(observations);
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public List<ProductResource> getProductResources() {
		return productResources;
	}
	
	public ProductGroup getProductGroup() {
		return productGroup;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setBatch(String batch) {
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

	public void setPrice(Double price) {
		this.price = price;
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
	
	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}

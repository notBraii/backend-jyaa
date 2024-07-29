package models;

import javax.persistence.*;

import utils.Magnitude;

@Entity
public class ProductResource {
	@Id @GeneratedValue
	private Long id;
	
	@Embedded
	private Magnitude usedQuantity;
	
	@ManyToOne
	private StockResource stockResource;
	
	// Constructors
	public ProductResource() {
	}
	
	public ProductResource(Magnitude usedQuantity, StockResource stockResource) {
		this.usedQuantity = usedQuantity;
		this.stockResource = stockResource;
	}
	
	// Getters
	public Long getId() {
		return id;
	}
	
	public Magnitude getUsedQuantity() {
		return usedQuantity;
	}
	
	public StockResource getStockResource() {
		return stockResource;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUsedQuantity(Magnitude usedQuantity) {
		this.usedQuantity = usedQuantity;
	}
	
	public void setStockResource(StockResource stockResource) {
		this.stockResource = stockResource;
	}
	
}

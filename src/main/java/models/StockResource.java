package models;

import java.util.Date;
import utils.Magnitude;

import javax.persistence.*;

@Entity
public class StockResource {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	private Double price;
	
	@Embedded
	private Magnitude quantity;
	
	private Date enteredAt;
	private Date createdAt;
	
	@OneToOne
	private ResourceTag group;
	
	@OneToOne
	private ProducingFamily producingFamily;

	// Constructors
	public StockResource() {
	}
	
	public StockResource(String name, Double price, Magnitude quantity, Date enteredAt, Date createdAt,
			ResourceTag group, ProducingFamily producingFamily) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.enteredAt = enteredAt;
		this.createdAt = createdAt;
		this.group = group;
		this.producingFamily = producingFamily;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Magnitude getQuantity() {
		return quantity;
	}

	public Date getEnteredAt() {
		return enteredAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public ResourceTag getGroup() {
		return group;
	}

	public ProducingFamily getProducingFamily() {
		return producingFamily;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setQuantity(Magnitude quantity) {
		this.quantity = quantity;
	}

	public void setEnteredAt(Date enteredAt) {
		this.enteredAt = enteredAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setGroup(ResourceTag group) {
		this.group = group;
	}

	public void setProducingFamily(ProducingFamily producingFamily) {
		this.producingFamily = producingFamily;
	}

}

package models;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Delivery {
	@Id
	@GeneratedValue
	private Long id;

	private Integer stock;
	private Date deliverDate;

	@OneToOne
	private Product product;

	@OneToOne
	private SalesChannel channel;

	// Constructors
	public Delivery() {
	}

	public Delivery(Integer stock, Product product, Date deliverDate, SalesChannel channel) {
		this.stock = stock;
		this.product = product;
		this.deliverDate = deliverDate;
		this.channel = channel;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public Integer getStock() {
		return stock;
	}

	public Product getProduct() {
		return product;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public SalesChannel getChannel() {
		return channel;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public void setChannel(SalesChannel channel) {
		this.channel = channel;
	}
}
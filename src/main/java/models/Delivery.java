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

	public Delivery(Integer stock, Product producto, Date deliverDate, SalesChannel channel) {
		this.stock = stock;
		this.product = producto;
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

	public Product getProducto() {
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

	public void setProducto(Product producto) {
		this.product = producto;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public void setChannel(SalesChannel channel) {
		this.channel = channel;
	}
}
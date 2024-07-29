package models;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Log {
	@Id @GeneratedValue
    private Long id;
	
    private Date date;
    private String details;
    
    @OneToOne
    private Product product;

    // Constructors
    public Log() {
    }
    
    public Log(Date date, String details, Product product) {
        this.date = date;
        this.details = details;
        this.product = product;
    }

	// Getters
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getDetails() {
		return details;
	}

	public Product getProduct() {
		return product;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

    public void setDate(Date date) {
        this.date = date;
    }

	public void setDetails(String details) {
		this.details = details;
	}

    public void setProduct(Product product) {
        this.product = product;
    }
}

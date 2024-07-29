package models;

import java.util.Date;
import utils.Magnitude;

import javax.persistence.*;

@Entity
public class StockRawMaterial extends StockResource {
	private Boolean canFreeze;
	private Boolean prepared;
	private Date expiredAt;

	// Constructors
	public StockRawMaterial() {	
		super();
	}
	
	public StockRawMaterial(String name, Double price, Magnitude quantity, ResourceTag group,
			ProducingFamily producingFamily, Date enteredAt, Date createdAt, Boolean canFreeze, Boolean prepare,
			Date expiredAt) {
		super( name, price, quantity, enteredAt, createdAt, group, producingFamily);
		this.canFreeze = canFreeze;
		this.prepared = prepare;
		this.expiredAt = expiredAt;
	}

	// Getters
	public Boolean isCanFreeze() {
		return canFreeze;
	}

	public Boolean isPrepared() {
		return prepared;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	// Setters
	public void setCanFreeze(Boolean canFreeze) {
		this.canFreeze = canFreeze;
	}

	public void setPrepared(Boolean prepared) {
		this.prepared = prepared;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

}

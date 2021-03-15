package com.readably.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class cartItems {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    
    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name="siparis_id")
    private Siparis siparis;
    
    long total=0;    //total money of this cart item for example product=60$ quantity= 2 so total =120
   
    
    public long getTotal() {
		return total;
	}
	public void setTotal() {
		this.total = (long) (product.getOurPrice()*quantity);
	}
	
	public cartItems(Product product, int quantity) {
		super();
		
		this.product = product;
		this.quantity = quantity;
	}
	public cartItems() {
		super();
	}
	
	public Siparis getSiparis() {
		return siparis;
	}
	
	public void setSiparis(Siparis siparis) {
	    this.siparis = siparis;
	}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
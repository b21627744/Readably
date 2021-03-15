package com.adminportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class WishlistItems {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	//private Product product;
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;

	@OneToOne
    private Product product;
	
	public WishlistItems(Product product) {
		super();
		this.product = product;
	}
	public WishlistItems() {
		super();
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


}

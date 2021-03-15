package com.readably.model;

import javax.persistence.*;

@Entity
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private int discount;
	
	public Campaign() {
		
	}

	public Campaign(Long id, String category, int discount) {
		this.id = id;
		this.category = category;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	 
}

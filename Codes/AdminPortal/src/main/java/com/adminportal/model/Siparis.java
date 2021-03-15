package com.adminportal.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Transactional
public class Siparis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String first_name;
	private String second_name;
	private String phone;
	private String email;
	private String cart_num;
	private String city;
	private String country;
	private String address;
	private int zip_code;
	
	
	private String status="wait"; //	wait confirmed rejected
	
	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate() {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		     
		this.date = dtf.format(now);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	private double cartTotal;
	private double grandTotal;
	private double shipment_cost=3;
	
	public double getShipment_cost() {
		return shipment_cost;
	}

	public void setShipment_cost(double shipment_cost) {
		this.shipment_cost = shipment_cost;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}


	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	 @OneToMany(mappedBy="siparis", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	 @JsonIgnore
	 private List<cartItems> cartitems=new ArrayList<cartItems>();
	 
	 public User getUser() {
			return user;
		}

	 public void setUser(User user) {
		 this.user = user;
	 }
		
	 public List<cartItems> getCartitems() {
		 return cartitems;
	 }


	 public void setCartitems(List<cartItems> cartitems) {
		 this.cartitems = cartitems;
	 }
	
	
	
	
	
	 public Long getId() {
		 return id;
	 }


	 public void setId(Long id) {
		 this.id = id;
	 }


	 public String getFirst_name() {
		 return first_name;
	 }


	 public void setFirst_name(String first_name) {
		 this.first_name = first_name;
	 }


	 public String getSecond_name() {
		 return second_name;
	 }


	 public void setSecond_name(String second_name) {
		 this.second_name = second_name;
	 }


	 public String getPhone() {
		 return phone;
	 }


	 public void setPhone(String phone) {
		 this.phone = phone;
	 }


	 public String getEmail() {
		 return email;
	 }


	 public void setEmail(String email) {
		 this.email = email;
	 }



	 public String getCity() {
		 return city;
	 }


	 public void setCity(String city) {
		 this.city = city;
	 }


	 public String getCountry() {
		 return country;
	 }


	 public void setCountry(String country) {
		 this.country = country;
	 }


	 public String getAddress() {
		 return address;
	 }


	 public void setAddress(String address) {
		 this.address = address;
	 }


	 public int getZip_code() {
		 return zip_code;
	 }


	 public void setZip_code(int zip_code) {
		 this.zip_code = zip_code;
	 }


	


	
	public String getCart_num() {
		return cart_num;
	}


	public void setCart_num(String cart_num) {
		this.cart_num = cart_num;
	}




}

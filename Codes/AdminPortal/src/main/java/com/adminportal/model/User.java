package com.adminportal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adminportal.model.Authority;
import com.adminportal.model.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.adminportal.model.Comment;
import com.adminportal.model.WishlistItems;
import com.adminportal.model.cartItems;

@SuppressWarnings("serial")
@Entity
@Transactional
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private Long id;
	private String username;
	private String password;
	private String name;
	private String surname;
	
	@Column(name="email", nullable = false, updatable = false)
	private String email;
	private String address;
	private int age;	
	private String phoneNumber;
	private boolean enabled=true;
	private double cartTotal;
	private double grandTotal=3;
	private int bookcount;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<cartItems> cart = new ArrayList<cartItems>();;
    
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<WishlistItems> wishlist=new ArrayList<WishlistItems>();
    
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<Comment>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Siparis> siparis = new ArrayList<Siparis>();
    
    
    public List<Siparis> getSiparis() {
        return siparis;
    }
    public void setSiparis(List<Siparis> siparis) {
        this.siparis = siparis;
    }
    
    public void setCartTotal(double cartTotal) {
    	this.cartTotal=cartTotal;
    }
    public double getCartTotal() {
    	return cartTotal;
    	
    }
    
    public void setBookCount(int bookcount) {
    	this.bookcount=bookcount;
    }
    public int getBookCount() {
    	return bookcount;
    	
    }
    
    public void setGrandTotal(double grandTotal) {
    	this.grandTotal=grandTotal;
    }
    public double getGrandTotal() {
    	return grandTotal;
    	
    }
    
    public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
    
    public List<cartItems> getCart() {
        return cart;
    }
    public void setCart(List<cartItems> cart) {
        this.cart = cart;
    }
    
    public List<WishlistItems> getWishlist() {
        return wishlist;
    }
    public void setWishlist(List<WishlistItems> wishlist) {
        this.wishlist = wishlist;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorites = new HashSet<>();
		userRoles.forEach(ur -> authorites.add(new Authority(ur.getRole().getName())));
		
		return authorites;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}

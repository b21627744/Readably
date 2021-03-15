package com.readably.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Transactional
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private String publisher;
	private String publicationDate;
	private String category;
	private String isbn;
	private double listPrice;
	private double ourPrice;
	private int pageNumber;
	private int stockNumber;
	private int sellercount;
	
	private int discount;
	
	private int rate=0;
	
	
	@Column(columnDefinition="text")
	private String description;
	
	@Transient
	private MultipartFile productImage;
	

	private String listPriceTemporary;

	private String ourPriceTemporary;

	private String stockNumberTemporary;
	
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Comment> comment = new ArrayList<Comment>();
	
	public Product() {
		
	}



	



	public Product(Long id, String title, String author, String publisher, String publicationDate, String category,
			String isbn, double listPrice, double ourPrice, int pageNumber, int stockNumber, int sellercount,
			int discount, int rate, String description, MultipartFile productImage, String listPriceTemporary,
			String ourPriceTemporary, String stockNumberTemporary, List<Comment> comment) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.category = category;
		this.isbn = isbn;
		this.listPrice = listPrice;
		this.ourPrice = ourPrice;
		this.pageNumber = pageNumber;
		this.stockNumber = stockNumber;
		this.sellercount = sellercount;
		this.discount = discount;
		this.rate = rate;
		this.description = description;
		this.productImage = productImage;
		this.listPriceTemporary = listPriceTemporary;
		this.ourPriceTemporary = ourPriceTemporary;
		this.stockNumberTemporary = stockNumberTemporary;
		this.comment = comment;
	}



	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	

	public int getDiscount() {
		return discount;
	}



	public void setDiscount(int discount) {
		this.discount = discount;
	}



	public List<Comment> getComment() {
		return comment;
	}


	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getPublicationDate() {
		return publicationDate;
	}


	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public double getListPrice() {
		return listPrice;
	}


	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}


	public double getOurPrice() {
		return ourPrice;
	}


	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getStockNumber() {
		return stockNumber;
	}


	public void setStockNumber(int stockNumber) {
		this.stockNumber = stockNumber;
	}

	public int getSellercount() {
		return sellercount;
	}


	public void setSellercount(int sellercount) {
		this.sellercount = sellercount;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	public String getListPriceTemporary() {
		return listPriceTemporary;
	}

	public void setListPriceTemporary(String listPriceTemporary) {
		this.listPriceTemporary = listPriceTemporary;
	}

	public String getOurPriceTemporary() {
		return ourPriceTemporary;
	}


	public void setOurPriceTemporary(String ourPriceTemporary) {
		this.ourPriceTemporary = ourPriceTemporary;
	}

	public String getStockNumberTemporary() {
		return stockNumberTemporary;
	}

	public void setStockNumberTemporary(String stockNumberTemporary) {
		this.stockNumberTemporary = stockNumberTemporary;
	}


	

	
	
}

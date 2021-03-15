package com.readably.model;

import org.springframework.web.multipart.MultipartFile;

import com.readably.config.SecurityConfig;

public class UserValidator {
	//direk user entitysi kullanmaktansa thymeleaf ten gelen veriler ilk bu varligi olusturulur daha sonra buradaki password onaylama gibi islemler ddogru ise bilgileri user a aktarılır.
	
	private Long id;
	private String confirm_Password;
	private String confirm_email;
	private String user_id;
	private String name;
	private String surname;
	private String email;
	private int age;
	private String phoneNumber;
	private String password;
	private String address;
	
	private MultipartFile userImage;
	
	private boolean imageControl = false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isImageControl() {
		return imageControl;
	}
	public void setImageControl(boolean imageControl) {
		this.imageControl = imageControl;
	}
	public MultipartFile getUserImage() {
		return userImage;
	}
	public void setUserImage(MultipartFile userImage) {
		this.userImage = userImage;
	}
	public String getConfirm_Password() {
		return confirm_Password;
	}
	public void setConfirm_Password(String confirm_Password) {
		this.confirm_Password = confirm_Password;
	}
	public String getConfirm_email() {
		return confirm_email;
	}
	public void setConfirm_email(String confirm_email) {
		this.confirm_email = confirm_email;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean areMailsEqual() {
		if(email.equals(confirm_email)) return true;
		else return false;
	}
	public boolean arePasswordsEqual() {
		if(password.equals(confirm_Password)) return true;
		else return false;
	}
	
	public boolean createUserSuccessfully(User user) {
		user.setAddress(address);
		user.setAge(age);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(SecurityConfig.passwordEncoder(password));
		user.setSurname(surname);
		user.setUsername(user_id);
		return true;
	}
}

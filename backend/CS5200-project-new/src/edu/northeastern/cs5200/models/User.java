package edu.northeastern.cs5200.models;

import java.util.Collection;
import java.util.Date;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String email;
	private Date dob;
	private Collection<Address> addresses;
	private Collection<Phone> phones;
	
	public User() {
		super();
	}
	
	public User(int userId, String firstName, String lastName, String username, String password, java.sql.Date dob, String email,
			Collection<Address> addresses, Collection<Phone> phones) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.addresses = addresses;
		this.phones = phones;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.sql.Date getDob() {
		return (java.sql.Date) dob;
	}
	public void setDob(java.sql.Date dob) {
		this.dob = dob;
	}
	

	public Collection<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Collection<Address> addresses) {
		this.addresses = addresses;
	}

	public Collection<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Collection<Phone> phones) {
		this.phones = phones;
	}	
	
}

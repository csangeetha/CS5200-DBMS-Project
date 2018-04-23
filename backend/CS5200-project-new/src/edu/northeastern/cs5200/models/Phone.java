package edu.northeastern.cs5200.models;

public class Phone {
	private int phoneId;
	private int areacode;
	private int phoneNumber;
	
	public Phone() {
		super();
	}
	
	public Phone(int phoneId, int areacode, int phoneNumber) {
		super();
		this.phoneId = phoneId;
		this.areacode = areacode;
		this.phoneNumber = phoneNumber;
	}

	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	public int getAreacode() {
		return areacode;
	}

	public void setAreacode(int areacode) {
		this.areacode = areacode;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}

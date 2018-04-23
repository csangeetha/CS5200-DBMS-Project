package edu.northeastern.cs5200.models;

public class Promotion {
	private int promotionId;
	private Restaurant resturant;
	public int getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
	public Restaurant getResturant() {
		return resturant;
	}
	public void setResturant(Restaurant resturant) {
		this.resturant = resturant;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private Customer customer;
	private String code;
}

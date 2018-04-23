package edu.northeastern.cs5200.models;

import java.util.Collection;

public class Order {
	private int orderId;
	private Collection<FoodRecipe> foodOrdered;
	private String paymentType;
	private int orderTotal;
	private int promotion;
	private String status;
	private String dateOfOrder;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getDateOfOrder() {
		return dateOfOrder;
	}
	public void setDateOfOrder(String dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	public Collection<FoodRecipe> getFoodOrdered() {
		return foodOrdered;
	}
	public void setFoodOrdered(Collection<FoodRecipe> foodOrdered) {
		this.foodOrdered = foodOrdered;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public int getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(int orderTotal) {
		this.orderTotal = orderTotal;
	}
	public int getPromotion() {
		return promotion;
	}
	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

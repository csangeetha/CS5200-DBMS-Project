package edu.northeastern.cs5200.models;

import java.util.Collection;

public class Customer extends User{
	private int customerId;
	private Collection<Order> orders;
	private Collection<Favorite> favorites;
	private Collection<RateAndReview> rateAndReviews;
	private Collection<Promotion> promotions;
	
	public Collection<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Collection<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	public Collection<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(Collection<Favorite> favorites) {
		this.favorites = favorites;
	}

	public Collection<RateAndReview> getRateAndReviews() {
		return rateAndReviews;
	}

	public void setRateAndReviews(Collection<RateAndReview> rateAndReviews) {
		this.rateAndReviews = rateAndReviews;
	}

	public Customer() {
		super();
	}
	
	public Customer(int customerId) {
		super();
		this.customerId = customerId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}

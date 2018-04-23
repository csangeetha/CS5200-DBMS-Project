package edu.northeastern.cs5200.models;

import java.util.Collection;

public class Restaurant {
	private int restaurantId;
	private String restaurantName;
	private int numberOfFavorites;
	private int rating;
	private Collection<Phone> phones;
	private Collection<Address> addresses;
	public int getRestaurantId() {
		return restaurantId;
	}
	public Restaurant() {
		super();
	}
	public Restaurant(int restaurantId, String restaurantName, int numberOfFavorites, int rating) 
	{
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.numberOfFavorites = numberOfFavorites;
		this.rating = rating;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public int getNumberOfFavorites() {
		return numberOfFavorites;
	}
	public void setNumberOfFavorites(int numberOfFavorites) {
		this.numberOfFavorites = numberOfFavorites;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Collection<Phone> getPhones() {
		return phones;
	}
	public void setPhones(Collection<Phone> phones) {
		this.phones = phones;
	}
	public Collection<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(Collection<Address> addresses) {
		this.addresses = addresses;
	}
	public Collection<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(Collection<Promotion> promotions) {
		this.promotions = promotions;
	}
	public Collection<FoodMenu> getFoodMenus() {
		return foodMenus;
	}
	public void setFoodMenus(Collection<FoodMenu> foodMenus) {
		this.foodMenus = foodMenus;
	}
	public Collection<Order> getOrders() {
		return orders;
	}
	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	public Collection<Chef> getChefs() {
		return chefs;
	}
	public void setChefs(Collection<Chef> chefs) {
		this.chefs = chefs;
	}
	private Collection<Promotion> promotions;
	private Collection<FoodMenu> foodMenus;
	private Collection<Order> orders;
	private Collection<Chef> chefs;
	

}

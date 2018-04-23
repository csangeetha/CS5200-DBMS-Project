package edu.northeastern.cs5200.models;

public class Favorite {
	private int favoriteId;
	private Customer customer;
	private Restaurant restaurant;
	private FoodRecipe foodRecipe;
	public int getFavoriteId() {
		return favoriteId;
	}
	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public FoodRecipe getFoodRecipe() {
		return foodRecipe;
	}
	public void setFoodRecipe(FoodRecipe foodRecipe) {
		this.foodRecipe = foodRecipe;
	}

}

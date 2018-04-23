package edu.northeastern.cs5200.models;

import java.util.Collection;

public class FoodMenu {
	private int foodMenuId;
	private Restaurant restaurant;
	private Collection<Chef> chefs;
	private Collection<FoodRecipe> foodRecipes;
	
	public int getFoodMenuId() {
		return foodMenuId;
	}
	public void setFoodMenuId(int foodMenuId) {
		this.foodMenuId = foodMenuId;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Collection<Chef> getChefs() {
		return chefs;
	}
	public void setChefs(Collection<Chef> chefs) {
		this.chefs = chefs;
	}
	public Collection<FoodRecipe> getFoodRecipes() {
		return foodRecipes;
	}
	public void setFoodRecipes(Collection<FoodRecipe> foodRecipes) {
		this.foodRecipes = foodRecipes;
	}
	
}

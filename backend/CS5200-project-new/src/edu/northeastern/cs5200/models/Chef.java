package edu.northeastern.cs5200.models;

import java.util.Collection;

public class Chef extends User {
	private int chefId;
	private Restaurant restaurant;
	private Collection<FoodMenu> foodMenus;
	private Collection<FoodRecipe> foodRecipes;
	public Collection<FoodRecipe> getFoodRecipes() {
		return foodRecipes;
	}
	public void setFoodRecipes(Collection<FoodRecipe> foodRecipes) {
		this.foodRecipes = foodRecipes;
	}
	public int getChefId() {
		return chefId;
	}
	public void setChefId(int chefId) {
		this.chefId = chefId;
	}
	public Restaurant getResturant() {
		return restaurant;
	}
	public void setResturant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Collection<FoodMenu> getFoodMenus() {
		return foodMenus;
	}
	public void setFoodMenus(Collection<FoodMenu> foodMenus) {
		this.foodMenus = foodMenus;
	}
	
}

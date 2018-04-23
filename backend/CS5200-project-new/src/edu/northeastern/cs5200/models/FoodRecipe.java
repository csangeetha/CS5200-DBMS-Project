package edu.northeastern.cs5200.models;

import java.util.Collection;

public class FoodRecipe {
	private int foodRecipeId;
	private String name;
	private String ingredients;
	private String difficultyLevel;
	private int cookTime;
	private int numberOfFavorites;
	private Collection<Favorite> favorites;
	private Chef chef;
	private FoodMenu foodMenu;
	private int price;
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getFoodRecipeId() {
		return foodRecipeId;
	}
	public void setFoodRecipeId(int foodRecipeId) {
		this.foodRecipeId = foodRecipeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	public int getCookTime() {
		return cookTime;
	}
	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}
	public int getNumberOfFavorites() {
		return numberOfFavorites;
	}
	public void setNumberOfFavorites(int numberOfFavorites) {
		this.numberOfFavorites = numberOfFavorites;
	}
	public Collection<Favorite> getFavorites() {
		return favorites;
	}
	public void setFavorites(Collection<Favorite> favorites) {
		this.favorites = favorites;
	}
	public Chef getChef() {
		return chef;
	}
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	public FoodMenu getFoodMenu() {
		return foodMenu;
	}
	public void setFoodMenu(FoodMenu foodMenu) {
		this.foodMenu = foodMenu;
	}
}

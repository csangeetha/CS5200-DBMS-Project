package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.FoodRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodRecipeDao extends ConnectionDao {
	private static final String createFoodRecipe = "INSERT INTO food_recipe (name, ingredients, dificulty_level, cook_time, chef_id) VALUES(?,?,?,?,?)";
	private static final String updateFoodRecipe = "UPDATE food_recipe SET food_recipe.name = ?, food_recipe.ingredients = ?, food_recipe.dificulty_level = ?, food_recipe.cook_time = ?, food_recipe.numberOfFav = ? WHERE food_recipe.id = ?;";
	private static final String deleteFoodRecipe = "Delete FROM food_recipe WHERE food_recipe.id = ?;";
	private static final String findFoodRecipeId =	"SELECT * FROM food_recipe WHERE id = ?";
	private static final String findAllfoodRecipe = "SELECT * FROM food_recipe";
	
	private static FoodRecipeDao instance = null;
	private static Map<Integer, FoodRecipe> fr = new HashMap<>();
	public static Map<Integer, FoodRecipe> getFoodRecipes() {
		return fr;
	}
	private FoodRecipeDao() {}
	public static FoodRecipeDao getInstance() {
		if (instance == null) {
			instance = new FoodRecipeDao();
		}
		return instance;
	}
	
	public static int createFoodRecipe(Connection conn, int foodRecpId, FoodRecipe foodRecipe) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createFoodRecipe, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, foodRecipe.getName());
			statement.setString(2, foodRecipe.getIngredients());
			statement.setString(3, foodRecipe.getDifficultyLevel());
			statement.setInt(4, foodRecipe.getCookTime());
			statement.setInt(5, foodRecpId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				fr.put(id, foodRecipe);
				return id;
			}
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int updateFoodRecipe(Connection conn, int frId, FoodRecipe foodR) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateFoodRecipe);
			statement.setString(1, foodR.getName());
			statement.setString(2, foodR.getIngredients());
			statement.setString(3, foodR.getDifficultyLevel());
			statement.setInt(4, foodR.getCookTime());
			statement.setInt(5, foodR.getNumberOfFavorites());
			statement.setInt(3, frId);
			statement.executeUpdate();
			fr.put(frId, foodR);
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public static int deleteFoodRecipe(Connection conn, int frId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteFoodRecipe);
			statement.setInt(1,frId);
			statement.executeUpdate();
			fr.remove(frId);
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public static FoodRecipe findFoodRecipeById(Connection conn, int frId) {
		FoodRecipe fRecipe = new FoodRecipe();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findFoodRecipeId);
			statement.setInt(1, frId);
			result = statement.executeQuery();
			if (result.next()) {
				String recipeName = result.getString("name");
				String ingredients = result.getString("ingredients");
				String difficulty = result.getString("dificulty_level");
				int cookTime = result.getInt("cook_time");
				int numFav = result.getInt("numberOfFav");
				fRecipe.setName(recipeName);
				fRecipe.setIngredients(ingredients);
				fRecipe.setDifficultyLevel(difficulty);
				fRecipe.setCookTime(cookTime);
				fRecipe.setNumberOfFavorites(numFav);
			}
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return fRecipe;
	}
	
	public static List<FoodRecipe> findAllFoodRecipe(Connection conn) {
		List<FoodRecipe> fRecipes = new ArrayList<FoodRecipe>();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findAllfoodRecipe);
			result = statement.executeQuery();
			while (result.next()) {
				FoodRecipe fRecipe = new FoodRecipe();
				String recipeName = result.getString("name");
				String ingredients = result.getString("ingredients");
				String difficulty = result.getString("dificulty_level");
				int cookTime = result.getInt("cook_time");
				int numFav = result.getInt("numberOfFav");
				int price = result.getInt("price");
				int id = result.getInt("id");
				fRecipe.setName(recipeName);
				fRecipe.setIngredients(ingredients);
				fRecipe.setDifficultyLevel(difficulty);
				fRecipe.setCookTime(cookTime);
				fRecipe.setNumberOfFavorites(numFav);
				fRecipe.setFoodRecipeId(id);
				fRecipe.setPrice(price);
				fRecipes.add(fRecipe);
			}
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return fRecipes;
	}

}

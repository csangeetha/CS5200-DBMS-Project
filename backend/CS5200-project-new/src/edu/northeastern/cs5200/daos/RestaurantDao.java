package edu.northeastern.cs5200.daos;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.northeastern.cs5200.models.Restaurant;

import java.sql.Connection;

public class RestaurantDao extends ConnectionDao {
	private final static String createRestaurant = "INSERT INTO resturant (restaurantName, numberOfFavorites, rating) VALUES(?,?,?)";
	private final static String updateRestaurant = "UPDATE resturant SET resturant.name = ?, resturant.numberOfFavorites = ?, resturant.rating=?  WHERE resturant.id = ?";
	private final static String findRestaurantId = "SELECT * FROM resturant WHERE id = ?";
	private final static String deleteRestaurant = "DELETE FROM resturant WHERE resturant.id=?";
	
	private RestaurantDao() { }
	private static RestaurantDao instance = null;
	private static Map<Integer, Restaurant> restaurant = new HashMap<>();
	public static RestaurantDao getInstance() {
	    if(instance == null) {
	        instance = new RestaurantDao();
	    }
	    return instance;
	}
	
	public static Map<Integer, Restaurant> getRestaurants() {
		return restaurant;
	}
	
	public static int createRestauraunt(Connection conn,Restaurant rest) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createRestaurant);
			statement.setString(1, rest.getRestaurantName());
			statement.setInt(2, rest.getNumberOfFavorites());
			statement.setInt(3, rest.getRating());
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				restaurant.put(id, rest);
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
	
	public static int updateRestaurant(Connection conn, int restId, Restaurant rest) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateRestaurant);
			statement.setString(1, rest.getRestaurantName());
			statement.setInt(2, rest.getNumberOfFavorites());
			statement.setInt(3, rest.getRating());
			statement.setInt(4, restId);
			statement.executeUpdate();
			restaurant.put(restId, rest);
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
	
	public static int deleteRestaurant(Connection conn, int restId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteRestaurant);
			statement.setInt(1,restId);
			statement.executeUpdate();
			restaurant.remove(restId);
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
	
	public static Restaurant findRestaurantById(Connection conn, int restId) {
		Restaurant rest = new Restaurant();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findRestaurantId);
			statement.setInt(1, restId);
			result = statement.executeQuery();
			if (result.next()) {
				String restName = result.getString("restaurantName");
				int fav = result.getInt("numberOfFavorites");
				int rating = result.getInt("rating");
				rest.setRestaurantName(restName);
				rest.setNumberOfFavorites(fav);
				rest.setRating(rating);
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
		return rest;
	}

}

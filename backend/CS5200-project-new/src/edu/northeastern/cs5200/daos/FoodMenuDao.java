package edu.northeastern.cs5200.daos;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class FoodMenuDao extends ConnectionDao {
	private final String deleteFoodMenu = "DELETE FROM food_menu WHERE `resturant_id`=? AND `chef_id`=?";
	private final String assignFoodMenu = "INSERT INTO food_menu (`resturant_id`, `chef_id`) VALUES (?,?)";
	
	private static FoodMenuDao instance = null;
	private FoodMenuDao() { }
	public static FoodMenuDao getInstance() {
	    if(instance == null) {
	        instance = new FoodMenuDao();
	    }
	    return instance;
	}
	
	public int deleteFoodMenu(int restId, int chefId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			Connection conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(deleteFoodMenu);
			statement.setInt(1,restId);
			statement.setInt(2, chefId);
			res = statement.executeUpdate(); 
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}		
		return res;
	}
	
	public int assignFoodMenu(int restId, int chefId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			Connection conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(assignFoodMenu);
			statement.setInt(1, restId);
			statement.setInt(2, chefId);
			res = statement.executeUpdate(); 
			statement.close();
			conn.close();
			return res;
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}		
		return res;
	}

}

package edu.northeastern.cs5200.daos;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.northeastern.cs5200.models.Chef;
import edu.northeastern.cs5200.models.Customer;
import edu.northeastern.cs5200.models.CustomerOrder;
import edu.northeastern.cs5200.models.Order;
import edu.northeastern.cs5200.models.RateAndReview;
import edu.northeastern.cs5200.models.Restaurant;
import edu.northeastern.cs5200.models.User;

import java.sql.Connection;
import java.util.Date;

public class AdminDao extends ConnectionDao {
	private final static String findAllUsers = "SELECT * FROM user";
	private final static String deleteUser = "DELETE FROM user WHERE user.id =?";
	private final static String updateUser = "UPDATE user SET firstname=?,lastname=?,dob=?,email=?,username=?,password=? WHERE user.id=?";
	
	public static List<User> findAllUsers(Connection conn) {
		List<User> users = new ArrayList<User>();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findAllUsers);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				User user = new User();
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				user.setUserId(id);
				user.setFirstName(firstname);
				user.setLastName(lastname);
				user.setDob((java.sql.Date) dob);
				user.setEmail(email);
				user.setUsername(username);
				user.setPassword(password);
				user.setType(res.getString("type"));
				users.add(user);
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
		return users;
	}
	
	public static int deleteUser(Connection conn, int userId) {
		conn = null;
		int res=-1;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteUser);
			statement.setInt(1,userId);
			res=statement.executeUpdate();
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
	
	public static int updateUser(Connection conn, int userId, User user) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(updateUser);
			statement.setString(1,user.getFirstName());
			statement.setString(2,user.getLastName());
			statement.setDate(3,user.getDob());
			statement.setString(4,user.getEmail());
			statement.setString(5,user.getUsername());
			statement.setString(6,user.getPassword());
			statement.setInt(7,userId);
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

}

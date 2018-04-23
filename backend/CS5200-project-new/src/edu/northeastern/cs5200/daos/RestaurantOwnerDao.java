package edu.northeastern.cs5200.daos;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.northeastern.cs5200.models.Chef;
import edu.northeastern.cs5200.models.ResturantOwner;

import java.sql.Connection;
import java.sql.Date;

public class RestaurantOwnerDao extends ConnectionDao {
	private static final String createUser = "INSERT INTO user (firstname,lastname,dob,email,username,password,type) VALUES (?,?,?,?,?,?,?)";
	private static final String createOwner = "INSERT INTO resturant_owner (id) VALUES (?)";
	private static final String updateOwner = "UPDATE user join resturant_owner ON user.id = resturant_owner.id SET firstname=?,lastname=?,dob=?,email=?,username=?,password=? WHERE resturant_owner.id=?";
	private static final String findAllOwnerById = "SELECT * FROM user JOIN resturant_owner ON user.id = resturant_owner.id WHERE resturant_owner.id=?";
	private static final String deleteOwner = "DELETE from user WHERE id=?";
	private static final String findOwnerByCredentials="SELECT * FROM user JOIN resturant_owner ON user.id = resturant_owner.id WHERE user.username=? AND user.password=?";
	
	private RestaurantOwnerDao() { }
	private static RestaurantOwnerDao instance = null;
	
	public static RestaurantOwnerDao getInstance() {
	    if(instance == null) {
	        instance = new RestaurantOwnerDao();
	    }
	    return instance;
	}
	
	public static ResturantOwner findOwnerByCredentials(Connection conn,String userName, String pass) {
		ResturantOwner cust = new ResturantOwner();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findOwnerByCredentials);
			statement.setString(1, userName);
			statement.setString(2, pass);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				cust.setUserId(id);
				cust.setResturantOwnerId(id);
				cust.setFirstName(firstname);
				cust.setLastName(lastname);
				cust.setDob((java.sql.Date) dob);
				cust.setEmail(email);
				cust.setUsername(username);
				cust.setPassword(password);
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
		return cust;
	}
	
	
	public static int createOwner(Connection conn, ResturantOwner owner) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(createUser);
			statement.setString(1, owner.getFirstName());
			statement.setString(2, owner.getLastName());
			statement.setDate(3, owner.getDob());
			statement.setString(4, owner.getEmail());
			statement.setString(5, owner.getUsername());
			statement.setString(6, owner.getPassword());
			statement.setString(7, "owner");
			res = statement.executeUpdate(); 
			ResultSet keys = statement.getGeneratedKeys();
			int ownerId;
		    if(keys.next()) {
		    	ownerId = keys.getInt(1);
		    } 
		    else {
	            throw new SQLException("Owner ID not returned");
	        }
			statement.close();	
			PreparedStatement statement1 = null;
			statement1 = conn.prepareStatement(createOwner);
			statement1.setInt(1, ownerId);
			res = statement1.executeUpdate(); 
			statement1.close();
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
	
	public static int updateOwner(Connection conn, int ownerId, ResturantOwner owner) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(updateOwner);
			statement.setString(1,owner.getFirstName());
			statement.setString(2,owner.getLastName());
			statement.setDate(3,owner.getDob());
			statement.setString(4,owner.getEmail());
			statement.setString(5,owner.getUsername());
			statement.setString(6,owner.getPassword());
			statement.setInt(7,ownerId);
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
	
	public static ResturantOwner findOwnerById(Connection conn, int ownerId) {
		ResturantOwner owner = new ResturantOwner();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findAllOwnerById);
			statement.setInt(1, ownerId);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				owner.setResturantOwnerId(id);
				owner.setFirstName(firstname);
				owner.setLastName(lastname);
				owner.setDob(dob);
				owner.setEmail(email);
				owner.setUsername(username);
				owner.setPassword(password);
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
		return owner;
	}
	
	public static int deleteOwner(Connection conn, int ownerId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(deleteOwner);
			statement.setInt(1,ownerId);
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

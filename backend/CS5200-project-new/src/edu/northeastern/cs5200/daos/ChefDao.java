package edu.northeastern.cs5200.daos;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.northeastern.cs5200.models.Chef;
import edu.northeastern.cs5200.models.Customer;

import java.sql.Connection;
import java.sql.Date;

public class ChefDao extends ConnectionDao{
	private static final String createUser = "INSERT INTO user (firstname,lastname,dob,email,username,password,type) VALUES (?,?,?,?,?,?,?)";
	private static final String createChef = "INSERT INTO chef (id) VALUES (?)";
	private static final String updateChef = "UPDATE user join chef ON user.id = chef.id SET firstname=?,lastname=?,dob=?,email=?,username=?,password=? WHERE chef.id=?";
	private static final String findAllChefById = "SELECT * FROM user JOIN chef ON user.id = chef.id WHERE chef.id=?";
	private static final String deleteChef = "DELETE from user WHERE id=?";
	private static final String findChefByCredentials = "SELECT * FROM user JOIN chef ON user.id = chef.id WHERE user.username=? AND user.password=?";
	
	private ChefDao() { }
	private static ChefDao instance = null;
	
	public static ChefDao getInstance() {
	    if(instance == null) {
	        instance = new ChefDao();
	    }
	    return instance;
	}
	public static Chef findChefByCredentials(Connection conn,String userName, String pass) {
		Chef cust = new Chef();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findChefByCredentials);
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
				cust.setChefId(id);
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
	
	public static int createChef(Connection conn, Chef chef) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(createUser);
			statement.setString(1, chef.getFirstName());
			statement.setString(2, chef.getLastName());
			statement.setDate(3, chef.getDob());
			statement.setString(4, chef.getEmail());
			statement.setString(5, chef.getUsername());
			statement.setString(6, chef.getPassword());
			statement.setString(7, "chef");
			res = statement.executeUpdate(); 
			ResultSet keys = statement.getGeneratedKeys();
			int chefId;
		    if(keys.next()) {
		    	chefId = keys.getInt(1);
		    } 
		    else {
	            throw new SQLException("Chef ID not returned");
	        }
			statement.close();	
			PreparedStatement statement1 = null;
			statement1 = conn.prepareStatement(createChef);
			statement1.setInt(1, chefId);
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
	
	public static int updateChef(Connection conn, int chefId, Chef chef) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(updateChef);
			statement.setString(1,chef.getFirstName());
			statement.setString(2,chef.getLastName());
			statement.setDate(3,chef.getDob());
			statement.setString(4,chef.getEmail());
			statement.setString(5,chef.getUsername());
			statement.setString(6,chef.getPassword());
			statement.setInt(7,chefId);
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
	
	public static Chef findChefById(Connection conn, int chefId) {
		Chef chef = new Chef();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findAllChefById);
			statement.setInt(1, chefId);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				chef.setChefId(id);
				chef.setFirstName(firstname);
				chef.setLastName(lastname);
				chef.setDob(dob);
				chef.setEmail(email);
				chef.setUsername(username);
				chef.setPassword(password);
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
		return chef;
	}
	
	public static int deleteChef(Connection conn, int chefId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(deleteChef);
			statement.setInt(1,chefId);
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

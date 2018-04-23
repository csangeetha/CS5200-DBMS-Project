package edu.northeastern.cs5200.daos;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5200.models.Chef;
import edu.northeastern.cs5200.models.Customer;
import edu.northeastern.cs5200.models.FoodRecipe;
import edu.northeastern.cs5200.models.Order;
import edu.northeastern.cs5200.models.Restaurant;
import edu.northeastern.cs5200.models.ResturantOwner;

import java.sql.Connection;
import java.sql.Date;

public class RestaurantOwnerDao extends ConnectionDao {
	private static final String getAllFollowers="SELECT distinct customer_id,firstName,lastName,user.email FROM favorite_resturant JOIN resturant JOIN user ON favorite_resturant.resturant_id=resturant.id AND favorite_resturant.customer_id=user.id AND resturant.owner_id=?";
	
	private static final String getAllOrders="SELECT `order`.id as orderId, date_of_order,order_total,`status`, payment_type, restaurantName, food_recipe.name FROM dbms_project.`order` JOIN resturant JOIN food_recipe ON resturant_id=resturant.id AND food_recipe.id=food_id AND resturant.owner_id=?";
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
	
	public static List<Customer> getAllRestFollowers(Connection conn,int ownerId){
		List<Customer> customers = new ArrayList<Customer>();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(getAllFollowers);
			statement.setInt(1, ownerId);
			ResultSet res = statement.executeQuery();
			while(res.next()) {
				Customer cust = new Customer();
				int id=res.getInt("customer_id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				String email = res.getString("email");
				cust.setCustomerId(id);
				cust.setFirstName(firstname);
				cust.setLastName(lastname);
				cust.setEmail(email);
				customers.add(cust);
				
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
		return customers;
	}
	
	public static List<Order> getAllOrders(Connection conn,int ownerId){
		List<Order> orders = new ArrayList<Order>();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(getAllOrders);
			statement.setInt(1, ownerId);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				Order ord = new Order();
				String orderDate = result.getString("date_of_order");
				int ordTotal = result.getInt("order_total");
				String status = result.getString("status");
				String payment = result.getString("payment_type");
				ord.setOrderId(result.getInt("orderId"));
				Restaurant res = new Restaurant();
				FoodRecipe fr = new FoodRecipe();
				res.setRestaurantName(result.getString("restaurantName"));
				ord.setRes(res);
				fr.setName(result.getString("name"));
				ord.setFoodOrdered(fr);
				ord.setDateOfOrder(orderDate);
				ord.setOrderTotal(ordTotal);
				ord.setStatus(status);
				ord.setPaymentType(payment);
	
				orders.add(ord);
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
		return orders;
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

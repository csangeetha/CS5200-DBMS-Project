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
import java.util.HashMap;

public class CustomerDao extends ConnectionDao {
	private final static String userAddress = "SELECT * FROM address WHERE user_id=?";
	private final static String userPhone = "SELECT * FROM phone WHERE user_id=?";
	private final static String findUserByCredentials="SELECT * FROM user WHERE user.username=? AND user.password=?";
	private final static String makeChefFav = "INSERT INTO favorite_chef(customer_id , chef_id) VALUES (?,?)";
	private final static String deleteFavChef = "DELETE FROM favorite_chef WHERE favorite_chef.id=?";
	private final static String getFavChef = "SELECT favorite_chef.id as fav_id, chef_id , firstname, lastname,email, resturant_id FROM favorite_chef JOIN chef JOIN user ON favorite_chef.chef_id=chef.id AND chef.id=user.id WHERE customer_id=?";
	private final static String deleteFavRestaurant= "DELETE from favorite_resturant WHERE id=?";
	private final static String getFavRestaurant = "SELECT * FROM favorite_resturant fr JOIN resturant r ON fr.resturant_id = r.id where customer_id = ?";
	private final static String findReviewsOfCustomer="SELECT rate_and_review.id as reviewId ,title,review , rate , order_id FROM dbms_project.rate_and_review JOIN `order` JOIN user ON rate_and_review.order_id=`order`.id AND `order`.user_id=user.id AND user.id=?";
	private final static String createUser = "INSERT INTO user (firstname,lastname,dob,email,username,password,type) VALUES (?,?,?,?,?,?,?)";
	private final static String createCustomer = "INSERT INTO customer (id) VALUES (?)";
	private final static String updateCustomer = "UPDATE user join customer ON user.id = customer.id SET firstname=?,lastname=?,dob=?,email=?,username=?,password=? WHERE customer.id=?";
	private final static String findAllCustomerById = "SELECT * FROM user JOIN customer ON user.id = customer.id WHERE customer.id=?";
	private final static String deleteCustomer = "DELETE from user WHERE id=?";
	private final static String findAllCustomer = "SELECT * FROM user JOIN customer ON user.id = customer.id";
	private final static String findCustomerByCredentials= "SELECT * FROM user JOIN customer ON user.id=customer.id WHERE user.username=? AND user.password=?";
	private final static String findOrdersOfCustomer ="SELECT `order`.id as order_id, date_of_order, order_total,`status`as status_del,payment_type, restaurantName, food_recipe.name as food\r\n" + 
			" FROM `order` JOIN resturant JOIN food_recipe  ON resturant.id=`order`.resturant_id AND `order`.food_id = food_recipe.id WHERE user_id=?";
	private CustomerDao() { }
	private static CustomerDao instance = null;

	public static CustomerDao getInstance() {
		if(instance == null) {
			instance = new CustomerDao();
		}
		return instance;
	}
	
	public static int makeChefFav(Connection conn, int custId, int chefId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(makeChefFav);
			statement.setInt(1, custId);
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
	
	public static int createCustomer(Connection conn,Customer cust) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(createUser);
			statement.setString(1, cust.getFirstName());
			statement.setString(2, cust.getLastName());
			statement.setDate(3, cust.getDob());
			statement.setString(4, cust.getEmail());
			statement.setString(5, cust.getUsername());
			statement.setString(6, cust.getPassword());
			statement.setString(7, "customer");
			res = statement.executeUpdate(); 
			ResultSet keys = statement.getGeneratedKeys();
			int custId;
			if(keys.next()) {
				custId = keys.getInt(1);
			} 
			else {
				throw new SQLException("Customer ID not returned");
			}
			statement.close();	
			PreparedStatement statement1 = null;
			statement1 = conn.prepareStatement(createCustomer);
			statement1.setInt(1, custId);
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

	public static int updateCustomer(Connection conn, int custId, Customer cust) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(updateCustomer);
			statement.setString(1,cust.getFirstName());
			statement.setString(2,cust.getLastName());
			statement.setDate(3,cust.getDob());
			statement.setString(4,cust.getEmail());
			statement.setString(5,cust.getUsername());
			statement.setString(6,cust.getPassword());
			statement.setInt(7,custId);
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

	public static int createFavoriteRestaurant(Connection conn, int custId, Restaurant rest) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			RestaurantDao resDao = RestaurantDao.getInstance();
			int resID= resDao.createRestauraunt(conn,17, rest);
			statement = conn.prepareStatement("INSERT INTO favorite_resturant(resturant_id,customer_id) VALUES (?,?) ");
			statement.setInt(1,resID);
			statement.setInt(2,custId);
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
	
	
	public static Collection<RateAndReview> findReviewsOfCustomer(Connection conn,int custId) {
		List<RateAndReview> reviews = new ArrayList<RateAndReview>();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findReviewsOfCustomer);
			statement.setInt(1, custId);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				RateAndReview review = new RateAndReview();
				int reviewId = res.getInt("reviewId");
				String reviewDesc = res.getString("review");
				String title = res.getString("title");
				int rate = res.getInt("rate");
				int orderId = res.getInt("order_id");
				Order order= new Order();
				order.setOrderId(orderId);
				review.setOrder(order);
				review.setTitle(title);
				review.setReview(reviewDesc);
				review.setRateAndReviewId(reviewId);
				review.setRating(rate);
				reviews.add(review);	
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
		return reviews;
	}
	
	public static Collection<CustomerOrder> findOrdersOfCustomer(Connection conn,int custId) {
		List<CustomerOrder> cOrders = new ArrayList<CustomerOrder>();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findOrdersOfCustomer);
			statement.setInt(1, custId);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				CustomerOrder cOrder = new CustomerOrder();
				int orderId = res.getInt("order_id");
				Date orderDate = res.getDate("date_of_order");
				int orderTotal = res.getInt("order_total");
				String status = res.getString("status_del");
				String paymentType = res.getString("payment_type");
				String restaurantname = res.getString("restaurantName");
				String food = res.getString("food");
				cOrder.setOrderId(orderId);
				cOrder.setFoodName(food);
				cOrder.setRestaurantName(restaurantname);
				cOrder.setOrderDate((java.sql.Date) orderDate);
				cOrder.setPaymentType(paymentType);
				cOrder.setStatus(status);
				cOrder.setOrderTotal(orderTotal);
				cOrders.add(cOrder);
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
		return cOrders;
	}
	
	public static Customer findCustomerById(Connection conn,int custId) {
		Customer cust = new Customer();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findAllCustomerById);
			statement.setInt(1, custId);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				cust.setCustomerId(id);
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
	
	public static int deleteFavRestaurant(Connection conn, int favId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(deleteFavRestaurant);
			statement.setInt(1,favId);
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
	
	public static User findUserByCredentials(Connection conn,String userName, String pass) {
		User cust = new User();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findUserByCredentials);
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
	public static Customer findCustomerByCredentials(Connection conn,String userName, String pass) {
		Customer cust = new Customer();
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findCustomerByCredentials);
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
				cust.setCustomerId(id);
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
	public static Collection<Customer> findAllCustomer(Connection conn) {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(findAllCustomer);
			ResultSet res = statement.executeQuery(); 
			while(res.next()) {
				Customer cust = new Customer();
				int id=res.getInt("id");
				String firstname = res.getString("firstname");
				String lastname = res.getString("lastname");
				Date dob = res.getDate("dob");
				String email = res.getString("email");
				String username = res.getString("username");
				String password = res.getString("password");
				cust.setCustomerId(id);
				cust.setFirstName(firstname);
				cust.setLastName(lastname);
				cust.setDob((java.sql.Date) dob);
				cust.setEmail(email);
				cust.setUsername(username);
				cust.setPassword(password);
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

	public static int deleteCustomer(Connection conn, int custId) {
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			PreparedStatement statement = null;
			statement = conn.prepareStatement(deleteCustomer);
			statement.setInt(1,custId);
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
	
	public static List<Restaurant> findFavRestaurant(Connection conn, int custId) {
		List<Restaurant> rests = new ArrayList<Restaurant>();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(getFavRestaurant);
			statement.setInt(1, custId);
			result = statement.executeQuery();
			while (result.next()) {
				Restaurant rest = new Restaurant();
				String restName = result.getString("restaurantName");
				int fav = result.getInt("numberOfFavorites");
				int rating = result.getInt("rating");
				rest.setFavIdRes(result.getInt("id"));
				rest.setRestaurantName(restName);
				rest.setNumberOfFavorites(fav);
				rest.setRating(rating);
				rests.add(rest);
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
		return rests;
	}
	
	public static List<Chef> findFavChef(Connection conn, int custId) {
		List<Chef> chefs = new ArrayList<Chef>();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(getFavChef);
			statement.setInt(1, custId);
			result = statement.executeQuery();
			while (result.next()) {
				Chef chef = new Chef();
				chef.setFirstName(result.getString("firstname"));
				chef.setLastName(result.getString("lastname"));
				chef.setFavId(result.getInt("fav_id"));
				chef.setChefId(result.getInt("chef_id"));
				chef.setEmail(result.getString("email"));
				chefs.add(chef);
				
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
		return chefs;
	}
	
	public static int deleteFavChef(Connection conn,int favcId) {
		conn = null;
		int val=-1;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteFavChef);
			statement.setInt(1, favcId);
			 val = statement.executeUpdate();
			statement.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return val;
		
	}
	


}

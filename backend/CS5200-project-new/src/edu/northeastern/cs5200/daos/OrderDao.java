package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.CustomerOrder;
import edu.northeastern.cs5200.models.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderDao extends ConnectionDao {
	private final static String createOrder = "INSERT INTO `order` (date_of_order, order_total,  status, payment_type, food_id, user_id, resturant_id) VALUES(?,?,?,?,?,?,?)";
	private final static String updateOrder = "UPDATE `order` SET `order`.date_of_order = ?, `order`.order_total = ?,  `order`.status = ?, `order`.payment_type = ? WHERE `order`.id = ?;";
	private final static String deleteOrder = "Delete FROM `order` WHERE `order`.id = ?";
	private final static String findOrderId =	"SELECT * FROM `order` WHERE id = ?";
	private final static String findAllOrder =	"SELECT `order`.id as order_id, date_of_order, order_total,`status`as status_del,payment_type, restaurantName, food_recipe.name as food\r\n" + 
			" FROM `order` JOIN resturant JOIN food_recipe  ON resturant.id=`order`.resturant_id AND `order`.food_id = food_recipe.id ";
	
	private static OrderDao instance = null;
	private static Map<Integer, Order> ordr = new HashMap<>();
	public static Map<Integer, Order> getOrders() {
		return ordr;
	}
	private OrderDao() {}
	public static OrderDao getInstance() {
		if (instance == null) {
			instance = new OrderDao();
		}
		return instance;
	}
	
	public static int createOrder(Connection conn, int userId, int restId, int fmId, Order ord) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createOrder, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, ord.getDateOfOrder());
			statement.setInt(2, ord.getOrderTotal());
			statement.setString(3, ord.getStatus());
			statement.setString(4, ord.getPaymentType());
			statement.setInt(5, fmId);
			statement.setInt(6, userId);
			statement.setInt(7, restId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				ordr.put(id, ord);
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
	
	public static int updateOrder(Connection conn, int ordId, Order ord) {
		conn = null;
		PreparedStatement statement = null;
		int res = -1;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateOrder);
			statement.setString(1, ord.getDateOfOrder());
			statement.setInt(2, ord.getOrderTotal());
			statement.setString(3, ord.getStatus());
			statement.setString(4, ord.getPaymentType());
			statement.setInt(5, ordId);
			res = statement.executeUpdate();
			ordr.put(ordId, ord);
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
	
	public static int deleteOrder(Connection conn, int ordId) {
		conn = null;
		int res=-1;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteOrder);
			statement.setInt(1,ordId);
			res= statement.executeUpdate();
			ordr.remove(ordId);
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
	
	public static Order findOrderById(Connection conn, int ordId) {
		Order ord = new Order();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findOrderId);
			statement.setInt(1, ordId);
			result = statement.executeQuery();
			if (result.next()) {
				String orderDate = result.getString("date_of_order");
				int ordTotal = result.getInt("order_total");
				int promoId = result.getInt("promotion_id");
				String status = result.getString("status");
				String payment = result.getString("payment_type");
				ord.setDateOfOrder(orderDate);
				ord.setOrderTotal(ordTotal);
				ord.setStatus(status);
				ord.setPaymentType(payment);
				ord.setPromotion(promoId);		
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
		return ord;
	}
	
	public static Collection<CustomerOrder>  findAllOrder(Connection conn) {
		List<CustomerOrder> cOrders = new ArrayList<CustomerOrder>();
		conn = null;
		PreparedStatement statement = null;
		ResultSet res = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findAllOrder);
			res = statement.executeQuery();
			while (res.next()) {
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
	
	public static Order findOrderByCustomerId(Connection conn, int ordId) {
		Order ord = new Order();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findOrderId);
			statement.setInt(1, ordId);
			result = statement.executeQuery();
			if (result.next()) {
				String orderDate = result.getString("date_of_order");
				int ordTotal = result.getInt("order_total");
				int promoId = result.getInt("promotion_id");
				String status = result.getString("status");
				String payment = result.getString("payment_type");
				ord.setDateOfOrder(orderDate);
				ord.setOrderTotal(ordTotal);
				ord.setStatus(status);
				ord.setPaymentType(payment);
				ord.setPromotion(promoId);		
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
		return ord;
	}

}

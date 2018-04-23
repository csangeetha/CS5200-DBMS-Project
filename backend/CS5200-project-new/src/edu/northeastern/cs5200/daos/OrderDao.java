package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.Order;

import java.util.Date;
import java.util.HashMap;

public class OrderDao extends ConnectionDao {
	private final static String createOrder = "INSERT INTO `order` (date_of_order, order_total,  status, payment_type, food_id, user_id, resturant_id) VALUES(?,?,?,?,?,?,?)";
	private final static String updateOrder = "UPDATE `order` SET `order`.date_of_order = ?, `order`.order_total = ?,  `order`.status = ?, `order`.payment_type = ? WHERE `order`.id = ?;";
	private final static String deleteOrder = "Delete FROM order WHERE order.id = ?;";
	private final static String findOrderId =	"SELECT * FROM order WHERE id = ?";
	
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
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateOrder);
			statement.setString(1, ord.getDateOfOrder());
			statement.setInt(2, ord.getOrderTotal());
			statement.setString(3, ord.getStatus());
			statement.setString(4, ord.getPaymentType());
			statement.setInt(5, ordId);
			statement.executeUpdate();
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
		return 0;
	}
	
	public static int deleteOrder(Connection conn, int ordId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteOrder);
			statement.setInt(1,ordId);
			statement.executeUpdate();
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
		return 0;
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

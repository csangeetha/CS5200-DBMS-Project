package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.Promotion;
import java.util.HashMap;

public class PromotionDao extends ConnectionDao {
	private static final String createPromo = "INSERT INTO promotion (`code`, `customer_id`, `resturant_id`) VALUES(?,?,?)";
	private static final String updatePromo = "UPDATE promotion SET promotion.code = ? WHERE promotion.id = ?;";
	private static final String deletePromo = "Delete FROM promotion WHERE promotion.id = ?;";
	private static final String findPromoId =	"SELECT * FROM promotion WHERE id = ?";
	
	private static PromotionDao instance = null;
	private static Map<Integer, Promotion> promo = new HashMap<>();
	public static Map<Integer, Promotion> getPromotions() {
		return promo;
	}
	private PromotionDao() {}
	public static PromotionDao getInstance() {
		if (instance == null) {
			instance = new PromotionDao();
		}
		return instance;
	}
	
	public static int createPromo(Connection conn, int custId, int restId, Promotion pr) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createPromo, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, pr.getCode());
			statement.setInt(2, custId);
			statement.setInt(3, restId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				promo.put(id, pr);
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
	
	public static int updatePromo(Connection conn, int prId, Promotion pr) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updatePromo);
			statement.setString(1, pr.getCode());
			statement.setInt(2, prId);
			statement.executeUpdate();
			promo.put(prId, pr);
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
	
	public static int deletePromo(Connection conn, int prId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deletePromo);
			statement.setInt(1,prId);
			statement.executeUpdate();
			promo.remove(prId);
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
	
	public static Promotion findPromoById(Connection conn, int prId) {
		Promotion pr = new Promotion();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findPromoId);
			statement.setInt(1, prId);
			result = statement.executeQuery();
			if (result.next()) {
				String code = result.getString("code");
				pr.setCode(code);			
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
		return pr;
	}

}

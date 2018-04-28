package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.Phone;
import java.util.HashMap;

public class PhoneDao extends ConnectionDao {
	private static final String createPhone = "INSERT INTO phone (`areacode`, `phonenumber`, `user_id`) VALUES(?,?,?)";
	private static final String updatePhone = "UPDATE phone SET phone.areacode = ?, phone.phonenumber = ? WHERE phone.id = ?";
	private static final String deletePhone = "Delete FROM phone WHERE phone.id = ?;";
	private static final String findPhoneId =	"SELECT * FROM phone WHERE user_id = ?";
	private static PhoneDao instance = null;
	private static Map<Integer, Phone> phones = new HashMap<>();
	
	private PhoneDao() {}
	public static PhoneDao getInstance() {
		if (instance == null) {
			instance = new PhoneDao();
		}
		return instance;
	}
	
	public static Map<Integer, Phone> getPhones() {
		return phones;
	}
	
	public static int createPhone(Connection conn, int uId, Phone ph) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createPhone);
			statement.setInt(1, ph.getAreacode());
			statement.setInt(2, ph.getPhoneNumber());
			statement.setInt(3, uId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				phones.put(id, ph);
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
	
	public static int updatePhone(Connection conn, int phId, Phone ph) {
		conn = null;
		int res = -1;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updatePhone);
			statement.setInt(1, ph.getAreacode());
			statement.setInt(2, ph.getPhoneNumber());
			statement.setInt(3, phId);
			res = statement.executeUpdate();
			phones.put(phId, ph);
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
	
	public static int deletePhone(Connection conn, int phId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deletePhone);
			statement.setInt(1,phId);
			statement.executeUpdate();
			phones.remove(phId);
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
	
	public static Phone findPhoneById(Connection conn, int phId) {
		Phone ph = new Phone();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findPhoneId);
			statement.setInt(1, phId);
			result = statement.executeQuery();
			if (result.next()) {
				int id = result.getInt("id");
				int areacode = result.getInt("areacode");
				int phonenumber = result.getInt("phonenumber");
				ph.setPhoneId(id);
				ph.setAreacode(areacode);
				ph.setPhoneNumber(phonenumber);
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
		return ph;
	}

}

package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.Address;
import java.util.HashMap;

public class AddressDao extends ConnectionDao {
	private static final String createAddress = "INSERT INTO address (`street1`, `street2`, `city`, `state`, `zipcode`, `user_id`) VALUES(?,?,?,?,?,?)";
	private static final String updateAddress = "UPDATE address SET street1 = ?, street2 = ?, city = ?, state = ?, zipcode = ?, user_id = ? WHERE address.id = ?;";
	private static final String deleteAddress = "Delete FROM address WHERE address.id = ?;";
	private static final String findAddressId =	"SELECT * FROM address WHERE id = ?";
	private static AddressDao instance = null;
	private static Map<Integer, Address> address = new HashMap<>();
	
	private AddressDao() {}
	public static AddressDao getInstance() {
		if (instance == null) {
			instance = new AddressDao();
		}
		return instance;
	}
	
	public static Map<Integer, Address> getAddresses() {
		return address;
	}
	
	public static int createAddress(Connection conn, int uId, Address addr) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createAddress);
			statement.setString(1, addr.getStreet1());
			statement.setString(2, addr.getStreet2());
			statement.setString(3, addr.getCity());
			statement.setString(4, addr.getState());
			statement.setString(5, addr.getZipcode());
			statement.setInt(6, uId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				address.put(id, addr);
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
	
	public static int updateAddress(Connection conn, int addId, Address addr) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateAddress);
			statement.setString(1, addr.getStreet1());
			statement.setString(2, addr.getStreet2());
			statement.setString(3, addr.getCity());
			statement.setString(4, addr.getState());
			statement.setString(5, addr.getZipcode());
			statement.setInt(6, addId);
			statement.executeUpdate();
			address.put(addId, addr);
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
	
	public static int deleteAddress(Connection conn, int addId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteAddress);
			statement.setInt(1,addId);
			statement.executeUpdate();
			address.remove(addId);
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
	
	public static Address findAddressById(Connection conn, int addId) {
		Address addr = new Address();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findAddressId);
			statement.setInt(1, addId);
			result = statement.executeQuery();
			if (result.next()) {
				String street1 = result.getString("street1");
				String street2 = result.getString("street2");
				String city = result.getString("city");
				String state = result.getString("state");
				String zip = result.getString("zip");
				addr.setStreet1(street1);
				addr.setStreet2(street2);
				addr.setCity(city);
				addr.setState(state);
				addr.setZipcode(zip);
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
		return addr;
	}

}

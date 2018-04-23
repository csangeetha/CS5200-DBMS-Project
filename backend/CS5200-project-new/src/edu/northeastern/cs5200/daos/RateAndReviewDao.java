package edu.northeastern.cs5200.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Map;

import edu.northeastern.cs5200.models.RateAndReview;
import java.util.HashMap;

public class RateAndReviewDao extends ConnectionDao {
	private static final String createReview = "INSERT INTO rate_and_review (title, review, rate, order_id) VALUES(?,?,?,?)";
	private static final String updateReview = "UPDATE rate_and_review SET rate_and_review.title = ?, rate_and_review.review = ?, rate_and_review.rate = ? WHERE rate_and_review.id = ?;";
	private static final String deleteReview = "Delete FROM rate_and_review WHERE rate_and_review.id = ?;";
	private static final String findReviewId =	"SELECT * FROM rate_and_review WHERE id = ?";
	
	private static RateAndReviewDao instance = null;
	private static Map<Integer, RateAndReview> ratereview = new HashMap<>();
	
	private RateAndReviewDao() {}
	public static RateAndReviewDao getInstance() {
		if (instance == null) {
			instance = new RateAndReviewDao();
		}
		return instance;
	}
	public static Map<Integer, RateAndReview> getrateReviews() {
		return ratereview;
	}
	
	public static int createRateReview(Connection conn, int ordId, RateAndReview rr) {
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(createReview, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, rr.getTitle());
			statement.setString(2, rr.getReview());
			statement.setInt(3, rr.getRating());
			statement.setInt(4, ordId);
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			if(result.next()) {		   
				int id = result.getInt(1);
				ratereview.put(id, rr);
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
	
	public static int updateRateReview(Connection conn, int rrId, RateAndReview rr) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(updateReview);
			statement.setString(1, rr.getTitle());
			statement.setString(2, rr.getReview());
			statement.setInt(3, rr.getRating());
			statement.setInt(4, rrId);
			statement.executeUpdate();
			ratereview.put(rrId, rr);
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
	
	public static int deleteRateReview(Connection conn, int rrId) {
		conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(deleteReview);
			statement.setInt(1,rrId);
			statement.executeUpdate();
			ratereview.remove(rrId);
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
	
	public static RateAndReview findReviewById(Connection conn, int rrId) {
		RateAndReview rateReview = new RateAndReview();
		conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			Class.forName(jdbc_drvr);
			conn = DriverManager.getConnection(url, user_name, psswd);
			statement = conn.prepareStatement(findReviewId);
			statement.setInt(1, rrId);
			result = statement.executeQuery();
			if (result.next()) {
				String title = result.getString("title");
				String review = result.getString("review");
				int rating = result.getInt("rating");
				rateReview.setTitle(title);
				rateReview.setReview(review);
				rateReview.setRating(rating);
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
		return rateReview;
	}

}

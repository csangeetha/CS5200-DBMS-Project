package edu.northeastern.cs5200.models;

public class RateAndReview {
	private int rateAndReviewId;
	private Order order;
	private String title;
	private String review;
	private int rating;
	public int getRateAndReviewId() {
		return rateAndReviewId;
	}
	public void setRateAndReviewId(int rateAndReviewId) {
		this.rateAndReviewId = rateAndReviewId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

}

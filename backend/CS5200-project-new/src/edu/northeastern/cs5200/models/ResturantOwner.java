package edu.northeastern.cs5200.models;

import java.util.Collection;

public class ResturantOwner extends User{
	private int resturantOwnerId;
	private Collection<Restaurant> restaurants;
	public int getResturantOwnerId() {
		return resturantOwnerId;
	}
	public void setResturantOwnerId(int resturantOwnerId) {
		this.resturantOwnerId = resturantOwnerId;
	}
	public Collection<Restaurant> getResturants() {
		return restaurants;
	}
	public void setResturants(Collection<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
}

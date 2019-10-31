package com.sam.rest;

public class Favorites {
	private int userId;
	private String Remote;

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getRemote() {
		return this.Remote;
	}

	public void setRemote(String remote) {
		this.Remote = remote;
	}
}

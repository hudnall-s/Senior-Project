package com.sam.rest;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {

	@JsonProperty("userName")
	private String userName = "";
	@JsonProperty("email")
	private String email = "";
	@JsonProperty("password")
	private String password = "";

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty("password")
	public String getPassword() {
		return this.password;
	}

	@JsonProperty("userName")
	public void setUsername(String userName) {
		this.userName = userName;
	}

	@JsonProperty("userName")
	public String getUsername() {
		return this.userName;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("email")
	public String getEamil() {
		return this.email;
	}

}

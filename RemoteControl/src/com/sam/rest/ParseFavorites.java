package com.sam.rest;

import org.codehaus.jackson.annotate.JsonProperty;

public class ParseFavorites {
	@JsonProperty("UserId")
	private int UserId;
	@JsonProperty("remote")
	private String remote;

	@JsonProperty("UserId")
	public int getUserId() {
		return this.UserId;
	}

	@JsonProperty("remote")
	public String getRemote() {
		return this.remote;
	}

	@JsonProperty("UserId")
	public void setUserId(int UserId) {
		this.UserId = UserId;
	}

	@JsonProperty("remote")
	public void setRemote(String remote) {
		this.remote = remote;
	}

}

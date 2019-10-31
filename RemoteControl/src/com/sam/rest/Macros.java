package com.sam.rest;

public class Macros {
	private int macroId = 0;
	private int userId = 0;
	private String command = "";
	private String remote = "";

	public void setMacro(int macroId) {
		this.macroId = macroId;
	}

	public void setuserid(int user) {
		this.userId = user;
	}

	public void setcommand(String command) {
		this.command = command;
	}

	public void setremote(String remote) {
		this.remote = remote;
	}

	public int getMacro() {
		return this.macroId;
	}

	public int getuserId() {
		return this.userId;
	}

	public String getCommand() {
		return this.command;
	}

	public String getRemote() {
		return this.remote;
	}

}

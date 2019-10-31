package com.sam.rest;

import org.codehaus.jackson.annotate.JsonProperty;

public class ParseMacros {

	@JsonProperty("UserId")
	int UserId;
	@JsonProperty("MacroId")
	int MacroId;
	@JsonProperty("remote")
	String remote;
	@JsonProperty("Command")
	String Command;
	
	public int getUserId() {
		return this.UserId;
	}

	public int getMacroId() {
		return this.MacroId;
	}

	public String getRemote() {
		return this.remote;
	}

	public String command() {
		return this.Command;
	}
}

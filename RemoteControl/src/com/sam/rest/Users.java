package com.sam.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jettison.json.JSONObject;

@Path("/Users")
public class Users {

	@GET
	@Path("/")
	@Produces({ "application/json" })
	public JSONObject Users() {
		return Sql.queryUsers();
	}

	@GET
	@Path("/macros/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMacros(@PathParam("id") int id) {
		ArrayList<Macros> obj = Sql.getUserMacros(id);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}

	@GET
	@Path("/macros/{id}/{macroId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSingleMacro(@PathParam("id") int id, @PathParam("macroId") int macroId) {
		ArrayList<Macros> obj = Sql.getuserMacro(id, macroId);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}

	@PUT
	@Path("/macros/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String setMacros(ParseMacros m) {
		ArrayList<Macros> obj = Sql.addMacro(m.UserId, m.MacroId, m.remote, m.Command);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}

	@GET
	@Path("/favorites")
	@Produces(MediaType.APPLICATION_JSON)
	public String listFavorites() {
		ArrayList<Favorites> obj = Sql.getFavorites();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}

	@GET
	@Path("/favorites/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String listUserFavorite(@PathParam("userId") int userId) {
		ArrayList<Favorites> obj = Sql.getUserFavorite(userId);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);

	}

	@PUT
	@Path("/favorites")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String putUserFavorites(ParseFavorites f) {

		ArrayList<Favorites> obj = Sql.addUserFavorite(f.getUserId(), f.getRemote());
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addUser(User u) {
		return Sql.addUser(u.getUsername(), u.getPassword(), u.getEamil());
	}

}

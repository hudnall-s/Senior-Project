package com.sam.rest;

import java.util.ArrayList;
import com.sam.rest.Authentication.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/Users")
public class Users {

	@GET
	@Path("/authentication/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String testAuth(@HeaderParam("Authentication") String authHeader, @PathParam("id") int userId) {
		Authentication auth = new Authentication();
		boolean checkPass = false;
		try {
			checkPass = auth.checkPassword(auth.getHash(userId, authHeader), userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (checkPass == true) {
			return "Found Password";
		} else
			return "invalid Password";
	}

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
	@Path("/{id}/macros/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String setMacros(ParseMacros m, @PathParam("id") int userId) {
		Lirc l = new Lirc();

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		if (l.checkRemote(m.remote)) {
			ArrayList<Macros> obj = Sql.addMacro(userId, m.MacroId, m.remote, m.Command);

			return gson.toJson(obj);
		} else {
			JSONObject obj = new JSONObject();
			JSONObject obj1 = new JSONObject();
			try {
				obj1.put("ERROR-VALUE", 500);
				obj1.put("ERROR-DESC", "Remote Not Found");
				obj.put("ERROR", obj1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj.toString();
		}
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
	@Path("/{userId}/favorites")
	@Produces(MediaType.APPLICATION_JSON)
	public String listUserFavorite(@PathParam("userId") int userId) {
		ArrayList<Favorites> obj = Sql.getUserFavorite(userId);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);

	}

	@PUT
	@Path("/{userId}/favorites")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String putUserFavorites(ParseFavorites f, @PathParam("userId") int userId) {
		Lirc l = new Lirc();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		if (l.checkRemote(f.getRemote())) {
			ArrayList<Favorites> obj = Sql.addUserFavorite(userId, f.getRemote());

			return gson.toJson(obj);
		} else {
			JSONObject obj = new JSONObject();
			JSONObject obj1 = new JSONObject();
			try {

				obj1.put("ERROR-VALUE", 500);
				obj1.put("ERROR-DESC", "Remote Not Found");
				obj.put("ERROR", obj1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return obj.toString();
		}
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addUser(User u) {
		return Sql.addUser(u.getUsername(), u.getPassword(), u.getEamil());
	}

}

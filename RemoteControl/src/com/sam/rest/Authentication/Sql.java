package com.sam.rest.Authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sam.rest.Favorites;

public class Sql {
	private static Connection connect() {
		return com.sam.rest.Sql.connect();
	}

	public static String getUserPassHash(int userId) {
		String line = "SELECT * FROM seniorProject.Users WHERE userId = '" + userId + "';";
		Connection con = connect();
		Statement stmt;
		String ret = "";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {
				ret = rs.getString("userPassword");
			}
			con.close();
		} catch (Exception E) {

		}
		return ret;
	}

	public static String getSalt(int userId) {
		String line = "SELECT * from seniorProject.userHash where userId = " + userId + ";";
		Connection con = connect();
		Statement stmt;
		String ret = "";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {
				ret = rs.getString("userRandomHash");
			}
			con.close();
		} catch (SQLException E) {

		}
		return ret;
	}

	public static JsonObject insertSalt(int userId, String Salt) {
		JsonObject obj = new JsonObject();
		JsonArray arr = new JsonArray();
		JsonObject obj1 = new JsonObject();
		String line = "insert into seniorProject.userHash (`userId`, `userRandomHash`) values (" + userId + ", '" + Salt
				+ "');";
		Connection con = connect();

		Statement stmt;

		try {
			stmt = con.createStatement();
			stmt.execute(line);
			con.close();
			arr.add("Added Successfully");
			obj.add("data", arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			arr.add("Auto Add Failed");
			obj1.addProperty("error-code", "500");
			obj1.add("error-Desc", arr);
			obj.add("Error", obj1);
		}

		return obj;
	}
}

package com.sam.rest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sam.rest.Authentication.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Sql {

	public static Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seniorProject", "sp", "password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}

	public static JSONObject addUser(String name, String password, String email) {
		String line = "INSERT INTO seniorProject.Users ( `userName`, `userPassword`, `userEmail`) VALUES ('" + name
				+ "', 'temp', '" + email + "');";

		String line2 = "SELECT * FROM seniorProject.Users WHERE userName LIKE '" + name + "';";
		JSONObject ret = new JSONObject();
		Connection con = connect();
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.execute(line);
			ResultSet rs = stmt.executeQuery(line2);
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("userId");
				ret.put("Userid", rs.getObject("userId"));
				ret.put("Name", rs.getObject("userName"));
				ret.put("Email", rs.getObject("userEmail"));
			}

			Authentication auth = new Authentication();
			auth.generateHashSalt(id);
			String hash = auth.getHash(id, password);

			String line3 = "UPDATE `seniorProject`.`Users` SET `userPassword`='" + hash + "' WHERE `userId`= '" + id
					+ "';";

			stmt.execute(line3);

			con.close();
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			try {
				ret.put("ERROR", "Invalid UserID");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return ret;
	}

	private static int macroMaxNumber(int userId, int macroId) {
		Connection con = connect();
		String line = "SELECT MAX(runSequence) as run from seniorProject.userMacros WHERE userId =" + userId
				+ " and idMacro = " + macroId + ";";

		int number = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {
				number = rs.getInt("run");
			}
			return number;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return number;
	}

	public static ArrayList<Macros> addMacro(int UserId, int MacroId, String remote, String Command) {
		ArrayList<Macros> retObj = new ArrayList();

		int max = macroMaxNumber(UserId, MacroId);
		Connection con = connect();
		Statement stmt;
		String line2 = "SELECT * FROM seniorProject.userMacros WHERE userId = " + UserId + " AND idMacro = " + MacroId
				+ " ORDER By runSequence ASC; ";
		try {
			stmt = con.createStatement();
			String[] commands = Command.split(",");
			for (int i = 0; i < commands.length; i++) {

				max++;
				String line = "INSERT INTO seniorProject.userMacros(`idMacro`, `userId`, `remote`, `command`, `runSequence`) VALUES ("
						+ MacroId + "," + UserId + ",'" + remote + "','" + commands[i] + "'," + max + ");";
				stmt.execute(line);
			}

			ResultSet rs = stmt.executeQuery(line2);
			while (rs.next()) {
				Macros m = new Macros();
				m.setMacro(rs.getInt("idMacro"));
				m.setremote(rs.getString("remote"));
				m.setcommand(rs.getString("command"));
				m.setuserid(UserId);
				retObj.add(m);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retObj;

	}

	public static ArrayList<Macros> getUserMacros(int userId) {
		String line = "SELECT * FROM seniorProject.userMacros WHERE userId = " + userId + " ORDER By idMacro ASC; ";
		ArrayList<Macros> retObj = new ArrayList();

		Connection con = connect();

		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {

				Macros m = new Macros();
				m.setMacro(rs.getInt("idMacro"));
				m.setremote(rs.getString("remote"));
				m.setcommand(rs.getString("command"));
				m.setuserid(userId);
				retObj.add(m);

			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retObj;
	}

	public static ArrayList<Macros> getuserMacro(int userId, int macroId) {
		String line = "SELECT * FROM seniorProject.userMacros WHERE userId = " + userId + " AND idMacro = " + macroId
				+ " ORDER By idMacro ASC; ";
		ArrayList<Macros> retObj = new ArrayList();

		Connection con = connect();

		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {

				Macros m = new Macros();
				m.setMacro(rs.getInt("idMacro"));
				m.setremote(rs.getString("remote"));
				m.setcommand(rs.getString("command"));
				m.setuserid(userId);
				retObj.add(m);

			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retObj;
	}

	public static JSONObject queryUsers() {
		String line = "SELECT * FROM seniorProject.Users;";

		JSONObject retobj = new JSONObject();
		Connection con = connect();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			int ctr = 0;

			while (rs.next()) {
				JSONObject ret = new JSONObject();
				ret.put("Userid", rs.getObject("userId"));
				ret.put("Name", rs.getObject("userName"));
				ret.put("Email", rs.getObject("userEmail"));
				retobj.put("row " + ctr, ret);
				ctr++;

			}
			con.close();
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retobj;
	}

	public static ArrayList<Favorites> addUserFavorite(int userId, String remote) {
		String line = "INSERT INTO `seniorProject`.`userFavorites` (`userId`, `Remote`) VALUES (" + userId + ", '"
				+ remote + "');";
		ArrayList<Favorites> retObj = new ArrayList();
		Connection con = connect();
		Statement stmt;
		String line2 = "SELECT * FROM seniorProject.userFavorites WHERE userId = " + userId + ";";
		try {
			stmt = con.createStatement();
			stmt.execute(line);
			ResultSet rs = stmt.executeQuery(line2);
			while (rs.next()) {
				Favorites f = new Favorites();
				f.setUserId(rs.getInt("userId"));
				f.setRemote(rs.getString("Remote"));
				retObj.add(f);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retObj;

	}

	public static ArrayList<Favorites> getUserFavorite(int userId) {
		String line = "SELECT * FROM seniorProject.userFavorites WHERE userId = " + userId + ";";
		ArrayList<Favorites> retObj = new ArrayList();
		Connection con = connect();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {
				Favorites f = new Favorites();
				f.setUserId(rs.getInt("userId"));
				f.setRemote(rs.getString("Remote"));
				retObj.add(f);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retObj;
	}

	public static ArrayList<Favorites> getFavorites() {

		String line = "SELECT * FROM seniorProject.userFavorites";
		ArrayList<Favorites> retObj = new ArrayList();

		Connection con = connect();

		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(line);
			while (rs.next()) {

				Favorites f = new Favorites();
				f.setUserId(rs.getInt("userId"));
				f.setRemote(rs.getString("Remote"));
				retObj.add(f);

			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retObj;
	}
}

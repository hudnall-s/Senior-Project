package com.sam.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;

@Path("/lirc")
public class Lirc {

	@GET
	@Path("/remotes")
	@Produces({ "application/json" })
	public ArrayList<String> listLiveRemotes() {
		ArrayList<String> lines = new ArrayList<String>();

		// args for process to run
		String[] args = { "irsend", "LIST", "", "" };
		try {
			// call process builder
			Process proc = (new ProcessBuilder(args)).start();

			// read the input stream of the return
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			// declare a line variable
			String line = "";

			// while we still have lines
			while ((line = reader.readLine()) != null) {
				// if the line is not empty
				if (line != "")
					// add the line to the return lines
					lines.add(line);
			}
			reader.close();
		}
		// catch because its required
		catch (IOException e) {

			e.printStackTrace();
		}
		// return all the lines
		return lines;
	}

	@PUT
	@Path("/remotes/{brand}")
	@Produces({ "application/json" })
	public ArrayList<String> loadRemotes(@PathParam("brand") String brand) {

		// we need to read some directories so lets call a reader
		ReadDirectory d = new ReadDirectory();
		// declare a hashset to store the brands
		Set<String> remoteBrands = new HashSet<String>();
		try {
			// time to read the directory
			remoteBrands = d.readDirectoryFiles("/etc/lirc/remotes/" + brand);
		} catch (IOException e) {

			e.printStackTrace();
		}
		// convert the hashset to an arraylist
		ArrayList<String> remotes = new ArrayList<String>(remoteBrands);
		// used later to store the restart
		ArrayList<String> lines = new ArrayList<String>();

		// while we have remotes, lets loop through them
		for (int i = 0; i < remotes.size(); i++) {
			// we need a source and dest to copy to / from
			String destDir = "/etc/lirc/lircd.conf.d";

			String sourceDir = "/etc/lirc/remotes/" + brand + "/" + remotes.get(i);
			lines.add(remotes.get(i));
			File dest = new File(destDir);
			File source = new File(sourceDir);

			// time to copy the file to the directory... using a handy built in function to
			// do so
			try {
				FileUtils.copyFileToDirectory(source, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// lets restart lircd
		String[] args = { "service", "lircd", "restart" };
		try {
			Process proc = (new ProcessBuilder(args)).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line != "") {
					lines.add(line);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		// return anything that warrents to be returned
		return lines;
	}

	@GET
	@Path("/{remote}")
	@Produces({ "application/json" })
	public ArrayList<String> listRemoteButtons(@PathParam("remote") String remote) {
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<String> parsedLines = new ArrayList<String>();
		String[] args = { "irsend", "LIST", remote, "" };
		try {
			Process proc = (new ProcessBuilder(args)).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line != "")
					lines.add(line);
			}
			reader.close();

			for (int i = 0; i < lines.size() - 1; i++) {

				String[] tmp = ((String) lines.get(i)).split(" ");
				parsedLines.add(tmp[1]);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return parsedLines;
	}

	@GET
	@Path("/{remote}/{Command}")
	@Produces({ "text/plain" })
	public String sendCommand(@PathParam("remote") String remote, @PathParam("Command") String command) {
		ArrayList<String> lines = new ArrayList<String>();

		String[] args = { "irsend", "SEND_ONCE", remote, command };
		try {
			Process proc = (new ProcessBuilder(args)).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line != "") {
					lines.add(line);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (lines.size() > 0) {
			return "Failed to send";
		}
		return "Sent";
	}

}

package com.sam.rest;

import com.sam.rest.ReadDirectory;
import com.sam.rest.Remotes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

@Path("/remotedb")
public class Remotes {
	@GET
	@Path("/")
	@Produces({ "text/html" })
	public InputStream documentation() {
		try {
			File f = new File(getClass().getClassLoader().getResource("documentation.html").getFile());

			return new FileInputStream(f);
		} catch (FileNotFoundException e) {

			return null;
		}
	}

	@GET
	@Path("/remotedb")
	@Produces({ "application/json" })
	public String[] getRemotes() {
		ReadDirectory d = new ReadDirectory();

		Set<String> remoteBrands = new HashSet<String>();
		try {
			remoteBrands = d.readDirectory("/etc/lirc/remotes");
		} catch (IOException e) {

			e.printStackTrace();
		}

		String remotes = remoteBrands.toString();
		return remotes.split(",");
	}

	@GET
	@Path("/remotedb/{brand}")
	@Produces({ "application/json" })
	public String[] getBrandRemotes(@PathParam("brand") String brand) {
		ReadDirectory d = new ReadDirectory();

		Set<String> remoteBrands = new HashSet<String>();
		try {
			remoteBrands = d.readDirectoryFiles("/etc/lirc/remotes/" + brand);
		} catch (IOException e) {

			e.printStackTrace();
		}

		String remotes = remoteBrands.toString();
		return remotes.split(",");
	}

}

package com.sam.rest;

import com.sam.rest.Documentation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class Documentation {
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
}

package com.sam.rest;

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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.container.servlet.ServletContainer;

@Path("/")

public class Documentation
{
	  @GET 
	   @Path("/") 
	   @Produces(MediaType.TEXT_HTML) 
	   public InputStream documentation()
	   {
		   try {
	      File f = new File(
	    			getClass().getClassLoader().getResource("documentation.html").getFile()
	    			);
	     
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	   }
}

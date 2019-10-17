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



@Path("/remotes") 

public class Remotes {

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
	
	   @GET 
	   @Path("/remote") 
	   @Produces(MediaType.APPLICATION_JSON) 
	   public String[] getRemotes(){
		 ReadDirectory d = new ReadDirectory();
		 
		 
		 Set<String> remoteBrands = new HashSet<String>();
		 try {
			remoteBrands = d.readDirectory("/etc/lirc/remotes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		String remotes = remoteBrands.toString();
		String[] remoteArr = remotes.split(","); 
		return remoteArr; 
	      
	   }  
	   
	   @GET
	   @Path("/remote/{brand}")
	   @Produces(MediaType.APPLICATION_JSON)
	   public String[] getBrandRemotes(@PathParam("brand") String brand)
	   {
		   ReadDirectory d = new ReadDirectory();
		   
		   
			 Set<String> remoteBrands = new HashSet<String>();
			 try {
				remoteBrands = d.readDirectoryFiles("/etc/lirc/remotes/" + brand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 
			String remotes = remoteBrands.toString();
			String[] remoteArr = remotes.split(","); 
			return remoteArr; 
	   }

	   @GET
	   @Path("/listRemotes")
	   @Produces(MediaType.APPLICATION_JSON)
	   public ArrayList<String> listLiveRemotes()
	   {
		   ArrayList<String> lines = new ArrayList<String>();
		   
		   String[] args = new String[] {"irsend", "LIST", "", ""};
		   try {
			Process proc = new ProcessBuilder(args).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			 String line = "";
		        while((line = reader.readLine()) != null ) {
		        	if (line != "")
		        			lines.add(line);
		        }
		   reader.close();
		   
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   return lines;
		   
	   }
	   
	   @GET
	   @Path("/sendCommand/{remote}")
	   @Produces(MediaType.APPLICATION_JSON)
	   public ArrayList<String> listRemoteButtons(@PathParam("remote") String remote)
	   {
		   ArrayList<String> lines = new ArrayList<String>();
		   
		   String[] args = new String[] {"irsend", "LIST", remote, ""};
		   try {
			Process proc = new ProcessBuilder(args).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			 String line = "";
		        while((line = reader.readLine()) != null ) {
		        	if (line != "")
		        			lines.add(line);
		        }
		   reader.close();
		   
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   return lines;
		   
	   }
	   
	   
	   
	   @PUT
	   @Path("/sendCommand/{remote}/{Command}")
	  
	   @Produces(MediaType.TEXT_PLAIN)
	   public String sendCommand(@PathParam("remote") String remote, @PathParam("Command") String command)
	   {
		   ArrayList<String> lines = new ArrayList<String>();
		   
		   String[] args = new String[] {"irsend", "SEND_ONCE", remote, command};
		   try {
			Process proc = new ProcessBuilder(args).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			 String line = "";
		        while((line = reader.readLine()) != null) {
		        	if (line != "")
		        		lines.add(line);
		        }
		   
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   if(lines.size() > 0)
			   return "Failed to send";
		   else
			   return "Sent";
	   }
	 
	
}

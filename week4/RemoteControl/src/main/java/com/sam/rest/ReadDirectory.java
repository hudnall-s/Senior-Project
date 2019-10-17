package com.sam.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ReadDirectory {
	public ReadDirectory()
	{
		
	}
	public Set<String> readDirectory(String dirName) throws IOException {
	   
	  
	  
	   Set<String> fileList = new HashSet<>();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirName))) {
	        for (Path path : stream) {
	            if (Files.isDirectory(path)) {
	                fileList.add(path.getFileName()
	                    .toString());
	            }
	        }
	    }
	    return fileList;
		}
	
	public Set<String> readDirectoryFiles(String dirName) throws IOException {
		   
		  
		  
		   Set<String> fileList = new HashSet<>();
		    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirName))) {
		        for (Path path : stream) {
		            if (!Files.isDirectory(path)) {
		                fileList.add(path.getFileName()
		                    .toString());
		            }
		        }
		    }
		    return fileList;
			}
	}

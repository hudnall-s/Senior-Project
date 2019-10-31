package com.sam.rest;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;






public class ReadDirectory
{
  public Set<String> readDirectory(String dirName) throws IOException {
    Set<String> fileList = new HashSet<String>();
    try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(Paths.get(dirName))) {
        for (java.nio.file.Path path : stream) {
            if (Files.isDirectory(path)) {
                fileList.add(path.getFileName()
                    .toString());
            }
        }
    }
    return fileList;
  }











  
  public Set<String> readDirectoryFiles(String dirName) throws IOException {
    Set<String> fileList = new HashSet<String>();
 
    try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(Paths.get(dirName))) {
        for (java.nio.file.Path path : stream) {
            if (!Files.isDirectory(path)) {
                fileList.add(path.getFileName()
                    .toString());
            }
        }
    }
    return fileList;
  }
}

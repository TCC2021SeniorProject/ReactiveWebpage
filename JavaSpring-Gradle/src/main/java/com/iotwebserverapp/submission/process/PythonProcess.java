package com.iotwebserverapp.submission.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PythonProcess {

	public void executePythonScript(String xmlFile, List<File> predefFiles) {
		System.out.println("File name:" + xmlFile.toString());
	    String cmd = "python3 ./ModelTranslator/src/main.py " + xmlFile.replace('\\', '/');
	    String s = null;
	    
	    if (predefFiles != null) {
		    for (int i = 0; i < predefFiles.size(); i++) {
		    	String fileName = predefFiles.get(i).toString();
		    	fileName = fileName.replaceAll(" ", "-");
		    	cmd += " " + fileName.replace('\\', '/');
		    }	
	    }
	    
	    System.out.println("Python command: " + cmd);
	    
	    try {
	    	System.out.println("-----------Running Python--------------\n");
	    	Process p = Runtime.getRuntime().exec(cmd);
	    	
	    	BufferedReader stdInput = new BufferedReader(
	    	  new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(
              new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
            	System.out.println(s);
            }
           
            // read any errors from the attempted command
            System.out.println("Standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
            	System.out.println(s);
            }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}

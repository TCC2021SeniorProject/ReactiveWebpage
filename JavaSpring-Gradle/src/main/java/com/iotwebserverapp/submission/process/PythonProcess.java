package com.iotwebserverapp.submission.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonProcess {
	
	//Default files
	final private File firstExample = new File("ModelTranslator/data/all_tran_example.xml");
	final private File secondExample = new File("ModelTranslator/data/multi_temp.xml");
	final private File thirdExample = new File("");
	final private File fourthExample = new File("");
	final private File fifthExample = new File("");
	
	public void selectDefaultPythonScript(final int exampleIndex) {
		switch (exampleIndex) {
			case (1):
				executeDefaultPythonScript(firstExample);
			case (2):
				executeDefaultPythonScript(secondExample);
			case (3):
				return;
			case (4):
				return;
			case (5):
				return;
			default:
				return;
		}
	}

	public void executeDefaultPythonScript(File xmlFile) {
	    try {
	    	String cmd = "python ModelTranslator/data/myscript.py";
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void executePythonScript(File xmlFile, String[] predefFiles) {
	    String cmd = "python3 ./ModelTranslator/src/main.py";
	    String s = null;
	    
	    try {
	    	System.out.println("Running Python");
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

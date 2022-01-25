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
				executePythonScript(firstExample, null);
				return;
			case (2):
				executePythonScript(secondExample, null);
				return;
			case (3):
				executePythonScript(thirdExample, null);
				return;
			case (4):
				executePythonScript(fourthExample, null);
				return;
			case (5):
				executePythonScript(fifthExample, null);
				return;
			default:
				executePythonScript(firstExample, null);
				return;
		}
	}
	
	public void executePythonScript(File xmlFile, String[] predefFiles) {
		String xmlFileName = xmlFile.toString();
	    String cmd = "python3 ./ModelTranslator/src/main.py " + xmlFileName;
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

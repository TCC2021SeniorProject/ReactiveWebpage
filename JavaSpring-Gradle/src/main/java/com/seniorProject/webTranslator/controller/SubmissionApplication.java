package com.seniorProject.webTranslator.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.seniorProject.webTranslator.util.FilesStorageService;
import com.seniorProject.webTranslator.process.PythonProcess;

@SpringBootApplication
@Controller
public class SubmissionApplication {
	
	String defaultOuputPath = "./ModelTranslator/data/output.py";
	String downloadedOutputPath = "./";
	private boolean fileSubmitted = false;
	
	//Key - ip, value - submitted files
	public static Map<String, String> userIP = new HashMap<>();
	
	//Storage service only initialized once
	public static FilesStorageService storageService = null;
	
	@GetMapping("/")
    public String  home() {
		//clear downloaded files, creates directory again
		storageService.init();
		fileSubmitted = false;
		
    	return "translator";
    }
     
	@GetMapping("/next")
    public String next(Map<String, Object> model) {
        model.put("message", "You found a hidden page!!");
        return "next";
    }
    
    @GetMapping("/viewScript")
    public String viewScript(Model model, HttpServletRequest request) {
    	String finalOutput = "";
        try {
        	
    		String ip = new ClientIPAddressController().getClientIPAddress(request);
    		System.out.println("View action requested from :" + ip);
    		String xmlFileName = userIP.get(ip);
            
        	File file;
        	if (fileSubmitted) {
        		String filePath = downloadedOutputPath + xmlFileName + ".py";
        		System.out.print("Viewing with submitted file name: " + filePath);
        		file = new File(xmlFileName + ".py");
        	} else {
        		System.out.print("Viewing with default file name");
        		file = new File(defaultOuputPath);
        	}
        	
        	Scanner in = new Scanner(file);
        	String line;
        	while (in.hasNextLine()) 
        	{ 
        		line = in.nextLine();
        		line = line.replaceAll("\t", "    ");
        		finalOutput+= line + "\n";
        	}
        	model.addAttribute("scriptViewer", finalOutput);
        	in.close();
        } catch (IOException e) {
        	String message = "File is not acceptable, cannot generate output\n";
        	model.addAttribute("submissionResult", message);
        }
        return "translator";
    }
 
    /**
     * Takes files from the website on user POST request
     * @param MultipartFile[] files
     * @param Model model
     * @return String web result
     */
    @PostMapping("/uploadMultiFile")
    public String uploadFile(@RequestParam("execFile") MultipartFile[] files, Model model, HttpServletRequest request) {
        String message = "";
        try {
            if (files.length == 0) {
            	message += "Non of the files are uploaded \nClicking view will display a default result\n";
            	model.addAttribute("submissionResult", message);
                return "web-translator";
            } else {
            	System.out.println("/nSubmitted number of files: " + files.length);
            }
        	
            Arrays.asList(files).stream().forEach(file -> {
            	if (file.isEmpty()) {
            		return;
            	}
                storageService.save(file);
                fileSubmitted = true;
            });   
            
            //load files from server's local path - Refactor this, consider not to save files to local server
            File xmlFile = storageService.getMultipartToFile(files[0]);
            String xmlFileName = xmlFile.toString().replaceAll(" ", "-");
            
            message += "Uploaded the file successfully: " + xmlFileName;
            
            xmlFileName = xmlFileName.toLowerCase();
            List<File> predefFiles = new ArrayList<>();
            for (int i = 1; i < files.length; i++) {
            	File predefFile = storageService.getMultipartToFile(files[i]);
            	if (predefFile != null) {
            		predefFiles.add(predefFile);
            		message += " " + predefFiles.toString();
            	}            	
            }
            
    		//Put user info in
    		String ip = new ClientIPAddressController().getClientIPAddress(request);
    		System.out.println("User :" + ip + " saved XML file named: " + xmlFileName);
    		userIP.put(ip, xmlFileName.replace('/', '\\'));
            
            //Python process
            PythonProcess pyProcess = new PythonProcess();
            pyProcess.executePythonScript(xmlFileName, predefFiles);
            model.addAttribute("submissionResult", message);
 
            fileSubmitted = true;
            
            return "translator";
        } catch (Exception e) {
            message = "Could not upload the files: " + e.toString();
            //Redirect to /html/web-translator and alert that this is not a valid file
            model.addAttribute("submissionResult", message);
            return "web-translator";
        }
    }
	
	public static void main(String[] args) {
		//Only single file storage service is allowed on multiple requests.
		if (storageService == null)
			storageService = new FilesStorageService().getInstance();
		storageService = new FilesStorageService();
		
		//Run application instance - port will be opened and never be reopened until program termination.
		SpringApplication.run(SubmissionApplication.class, args);
	}
}

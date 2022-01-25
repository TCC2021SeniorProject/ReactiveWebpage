package com.iotwebserverapp.submission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.iotwebserverapp.submission.service.FilesStorageServiceImpl;
import com.iotwebserverapp.submission.process.PythonProcess;
//
@SpringBootApplication
@Controller
public class SubmissionApplication {
	
	String defaultOuputPath = "./ModelTranslator/data/output.py";
	String downloadedOutputPath = "./uploads/output.py";
	boolean fileSubmitted = false;
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SubmissionApplication.class);
    }
	
    @Resource
	private FilesStorageServiceImpl storageService;
    
    //
	public static void main(String[] args) {
		SpringApplication.run(SubmissionApplication.class, args);
	}
	
    
	@GetMapping("/")
    public String  home() {
		storageService.init();
    	return "web-translator";
    }
	
    @GetMapping("/viewScript")
    public String viewScript(Model model) {
    	String finalOutput = "";
        try {
        	File file;
        	if (fileSubmitted) {
        		file = new File(downloadedOutputPath);
        	} else {
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
        	String message = "File currupted\n";
        	model.addAttribute("submissionResult", message);
        }
        return "web-translator";
    }
    
    @PostMapping("/uploadMultiFile")
    public String uploadFile(@RequestParam("execFile") MultipartFile[] files, Model model) {
        String message = "";
        System.out.println(files.length);
        try {
            List<String> fileNames = new ArrayList<>();
  
            Arrays.asList(files).stream().forEach(file -> {
            	if (file.isEmpty()) {
            		return;
            	}
        	    System.out.println("Uploading files");
                storageService.save(file);
                fileNames.add(file.getOriginalFilename());
            }); 
            
            if (fileNames.isEmpty()) {
            	message += "Non of the files are uploaded \nClicking view will display a default result\n";
            	model.addAttribute("submissionResult", message);
                PythonProcess pyProcess = new PythonProcess();
                pyProcess.executePythonScript(null, null);
                return "web-translator";
            }
            fileSubmitted = true;
            PythonProcess pyProcess = new PythonProcess();
            File xmlFile = storageService.getMultipartToFile(files[0]);
            pyProcess.executePythonScript(xmlFile, null);
            message += "Uploaded the file successfully: " + fileNames + "\n";
            model.addAttribute("submissionResult", message);
            return "web-translator";
        } catch (Exception e) {
            message = "Could not upload the files: " + e.toString();
            //Redirect to /html/web-translator and alert that this is not a valid file
            model.addAttribute("submissionResult", message);
            return "web-translator";
        }
    }
}

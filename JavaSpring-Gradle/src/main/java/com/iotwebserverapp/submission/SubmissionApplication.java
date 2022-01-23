package com.iotwebserverapp.submission;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iotwebserverapp.submission.message.ResponseMessage;
import com.iotwebserverapp.submission.service.FilesStorageServiceImpl;
import com.iotwebserverapp.submission.process.PythonProcess;
//
@SpringBootApplication
@Controller
public class SubmissionApplication {
	
	String defaultOuputPath = "./ModelTranslator/data/output.py";
	
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
		//Removes all local file
		storageService.deleteAll();
    	storageService.init();
    	return "web-translator";
    }
	
    @GetMapping("/viewScript")
    public String viewScript(Model model) {
    	String finalOutput = "";
        try {
        	File file = new File(defaultOuputPath);
        	Scanner in = new Scanner(file);
        	String line;
        	while (in.hasNextLine()) 
        	{ 
        		line = in.nextLine();
        		line = line.replaceAll("\t", "    ");
        		finalOutput+= line + "\n";
        	}
        	in.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        model.addAttribute("scriptViewer", finalOutput);
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
            PythonProcess pyProcess = new PythonProcess();
            pyProcess.executePythonScript(null, null);
            
            if (fileNames.isEmpty()) {
            	message += "Non of the files are uploaded \nClicking view will display a default result\n";
            	model.addAttribute("submissionResult", message);
                return "web-translator";
            }
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

package com.iotwebserverapp.submission.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@ComponentScan
public class FilesStorageService implements IFilesStorageService {

	private static FilesStorageService fileStorageInstance;
	private String ROOT_FOLDER_NAME = "uploads";
	private final Path ROOT_PATH = Paths.get(ROOT_FOLDER_NAME);

	@Override
	public FilesStorageService getInstance() {
		if (fileStorageInstance == null) {
			fileStorageInstance = new FilesStorageService();
		}
		return fileStorageInstance;
	}
	
	@Override
	public void init() {
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//Delete all existing files first
			deleteAll();
			Files.createDirectory(ROOT_PATH);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}


	
	//Save file to server's local path on client submission
	@Override
	public void save(MultipartFile file) {
		try {
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			filename = filename.toLowerCase().replaceAll(" ", "-");
			Path renamedPath = Paths
	                .get(ROOT_FOLDER_NAME + File.separator + filename);

			byte[] bytes = file.getBytes();
			Files.write(renamedPath, bytes);
			System.out.println("Saved file as: " + renamedPath);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	/**
	 * Load file from server's local path on client request
	 * 
	 * Assumes the output files is located at ./uploads/
	 * @param String filename
	 * @return org.springframework.core.io.Resource
	 */
	@Override
	public Resource load(String filename) {
		try {
			Path file = ROOT_PATH.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	/**
	 * Load file from server's local path for Python's process argument
	 * 
	 * Assumes the output files is located at ./uploads/
	 * @param String filename with no path
	 * @return File
	 */
	public File getMultipartToFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		Path copyLocation = Paths
                .get(ROOT_FOLDER_NAME + File.separator + filename);
		System.out.println("Getting file from: " + copyLocation);
		return copyLocation.toFile();
	}
	
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(ROOT_PATH.toFile());
	}
	
	@Override
	public Stream<Path> loadAll() {
		try {
	        return Files.walk(ROOT_PATH, 1).filter(path -> !path.equals(ROOT_PATH)).map(ROOT_PATH::relativize);
	    } catch (IOException e) {
	        throw new RuntimeException("Could not load the files!");
	    }
	}
}
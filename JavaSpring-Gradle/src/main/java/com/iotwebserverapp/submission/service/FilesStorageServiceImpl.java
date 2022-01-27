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
public class FilesStorageServiceImpl implements FilesStorageService {

	private String TMP_FOLDER = "uploads";
	private final Path root = Paths.get(TMP_FOLDER);

	@Override
	public void init() {
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//Delete all existing files first
			deleteAll();
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}


	@Override
	public void save(MultipartFile file) {
		try {
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			filename = filename.toLowerCase().replaceAll(" ", "-");
			Path copyLocation = Paths
	                .get(TMP_FOLDER + File.separator + filename);
			System.out.println(this.root.resolve(file.getOriginalFilename()));
			System.out.println(copyLocation);
			byte[] bytes = file.getBytes();
			Files.write(copyLocation, bytes);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
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

	public File getMultipartToFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		filename = filename.toLowerCase().replaceAll(" ", "-");
		Path copyLocation = Paths
                .get(TMP_FOLDER + File.separator + filename);
		return copyLocation.toFile();
	}
	
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}
	
	@Override
	public Stream<Path> loadAll() {
		try {
	        return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
	    } catch (IOException e) {
	        throw new RuntimeException("Could not load the files!");
	    }
	}
}
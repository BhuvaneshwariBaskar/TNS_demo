package com.tns.app1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tns.app1.service.FileProcessorService;

@RestController
public class App1Controller {
	
	@Autowired
	private FileProcessorService fileProcessorService;
	
	@PostMapping("/process-file")
	public ResponseEntity<String> processFiles(@RequestBody byte[] fileData){
		System.out.println("File received. File Size :  "+ fileData.length +" bytes");
		fileProcessorService.processFile(fileData);
		return ResponseEntity.ok("File process started");
	}
	
	@GetMapping("/health")
	public String healthCheck() {
		return "healthy";
	}
}

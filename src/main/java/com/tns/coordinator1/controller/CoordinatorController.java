package com.tns.coordinator1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tns.coordinator1.service.CoordinatorService;
import com.tns.coordinator1.service.HealthService;

@RestController
public class CoordinatorController {
	
	@Autowired
	private CoordinatorService coordinatorService;
	

	
	@PostMapping("/uploadfiles")
	public ResponseEntity<String> processUploadedFiles(@RequestParam("files") MultipartFile[] files){
		List<String> failedFiles = coordinatorService.processUploadedFiles(files);
		
		if(failedFiles.isEmpty()) {
			return ResponseEntity.ok("All files successfully processed.");
		}
		else {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("Some files can't be processed :" + failedFiles);
		}
 	} 
}

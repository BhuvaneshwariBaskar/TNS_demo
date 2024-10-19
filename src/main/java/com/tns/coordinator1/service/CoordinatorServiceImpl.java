package com.tns.coordinator1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CoordinatorServiceImpl implements CoordinatorService {
	@Autowired
	private HealthService healthService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<String> processUploadedFiles(MultipartFile[] files) {
		List<String> failedFiles = new ArrayList<>();
		for (MultipartFile file : files) {
			String healthyProcessorUrl = healthService.getHealthyProcessorUrl();

			if (healthyProcessorUrl != null) {
				for (int attempt = 1; attempt <= 3; attempt++) {
					try {
						byte[] fileBytes = file.getBytes();
						if (fileBytes != null && fileBytes.length > 0) {
							restTemplate.postForObject(healthyProcessorUrl + "/process-file", fileBytes, String.class);
							System.out.println("File " + file.getOriginalFilename() + " sent to " + healthyProcessorUrl
									+ " on attempt " + attempt);
							break;// Exit the loop if successful.
						} else {
							System.out.println("File " + file.getOriginalFilename() + " is empty.");
							failedFiles.add(file.getOriginalFilename());
						}

					} catch (Exception e) {
						System.out.println("Attempt " + attempt + " failed to send file: " + file.getOriginalFilename()
								+ " to processor " + healthyProcessorUrl);
						if (attempt == 3) {
							failedFiles.add(file.getOriginalFilename());
							System.out.println("Giving up after 3 attempts.");
						}
					}
				}

//				try {
//					restTemplate.postForObject(healthyProcessorUrl + "/process-file", file.getBytes(), String.class);
//					System.out.println("File " + file.getOriginalFilename() + "sent to" + healthyProcessorUrl);
//				} catch (Exception e) {
//					System.out.println("Failed to send file: " + file.getOriginalFilename() + " to processor"
//							+ healthyProcessorUrl);
//					e.printStackTrace();
//					failedFiles.add(file.getOriginalFilename());
//				}
			} else {
				System.out.println("No healthy processor found for file : " + file.getOriginalFilename());
				failedFiles.add(file.getOriginalFilename());
			}
		}
		return failedFiles;
	}
}

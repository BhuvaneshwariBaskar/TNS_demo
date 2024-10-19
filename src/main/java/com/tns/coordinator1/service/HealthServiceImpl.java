package com.tns.coordinator1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthServiceImpl implements HealthService {

	@Value("${processor.urls}")
	private List<String> processUrls;

	@Autowired
	private RestTemplate restTemplate;

	private int currentIndex = 0;

	@Override
	public String getHealthyProcessorUrl() {
		int startIndex = currentIndex;
		int size = processUrls.size();
		do {
			String url = processUrls.get(currentIndex);
			System.out.println("Checking health of processor at " + url);
			try {
				String status = restTemplate.getForObject(url + "/health", String.class);
				if ("healthy".equalsIgnoreCase(status)) {
					System.out.println("Processor " + url + " is healthy.");
					currentIndex = (currentIndex + 1) % size;
					return url;
				}
			} catch (Exception e) {
				System.out.println("Processor at " + url + " is not available or unhealthy.");
			}
			currentIndex = (currentIndex + 1) % size;
		} while (currentIndex != startIndex);
		return null;
	}

}

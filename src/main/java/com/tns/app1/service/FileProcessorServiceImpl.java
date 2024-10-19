package com.tns.app1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tns.app1.model.RecordEntity;
import com.tns.app1.repo.RecordRepository;

import jakarta.transaction.Transactional;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

	@Value("${file.processor.numThreads:4}")
	private int numThreads;

	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private EmailService emailService;

	private AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void processFile(byte[] fileData) {
		String fileContent = new String(fileData);
		List<String> records = List.of(fileContent.split("\n"));
		chunkRecords(records);
		System.out.println("done processing by processor1");
		emailService.sendStatusEmail("bhuvaneshwarib.cse@citchennai.net");
	}

	public void chunkRecords(List<String> records) {
		int chunkSize = records.size() / numThreads;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		for (int i = 0; i < numThreads; i++) {
			int start = i * chunkSize;
			int end = (i == numThreads - 1) ? records.size() : start + chunkSize;
			List<String> chunk = records.subList(start, end);
			executor.submit(() -> processChunk(chunk));
			System.out.println("Submitted task for partition :" + i);
		}

		executor.shutdown();
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
	}

	@Transactional
	public void processChunk(List<String> chunk) {
		List<RecordEntity> entitiesToSave = new ArrayList<>();
		for (String record : chunk) {
			String[] fields = record.split(",");
			if (fields.length >= 5) {
				String name = fields[1];
				String email = fields[2];
				Integer age = Integer.parseInt(fields[3]);
				String city = fields[4];
				RecordEntity entity = new RecordEntity(name, email, age, city);
				counter.incrementAndGet();
				entitiesToSave.add(entity);
			} else {
				System.out.println("Skipping incomplete record: " + record);
			}
		}

		if (!entitiesToSave.isEmpty()) {
			try {
				recordRepository.saveAll(entitiesToSave);
			} catch (Exception e) {
				System.err.println("Failed to save records: " + e.getMessage());
			}
		}
		System.out.println("Processed chunk, total records so far: " + counter.get());
	}
}

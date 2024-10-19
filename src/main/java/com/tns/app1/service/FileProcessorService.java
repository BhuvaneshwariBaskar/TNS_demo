package com.tns.app1.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tns.app1.model.RecordEntity;

@Service
public interface FileProcessorService {

	void processFile(byte[] fileData);

	void chunkRecords(List<String> records);
	
	void processChunk(List<String> chunk);
}

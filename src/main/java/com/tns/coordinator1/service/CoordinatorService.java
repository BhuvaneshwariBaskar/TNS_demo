package com.tns.coordinator1.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CoordinatorService {
	List<String> processUploadedFiles(MultipartFile[] files);
}

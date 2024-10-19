package com.tns.app1.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	void sendStatusEmail(String to);
}

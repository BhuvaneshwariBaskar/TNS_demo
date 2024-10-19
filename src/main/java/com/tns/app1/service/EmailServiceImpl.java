package com.tns.app1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendStatusEmail(String to) {
		String mailContent = "File Processed Successfully!!!";

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(mailContent, false);
			helper.setTo(to);
			helper.setSubject("Distributed Processing Works!");
			helper.setFrom("bhuvanavarsha96@gmail.com");
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

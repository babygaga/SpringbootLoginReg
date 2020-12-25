package com.security.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${spring.mail.username}")//app.propertiből vegy ki a küldő email címét 2sor összesen
	private String MESSAGE_FROM;

	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMessage(String email) {  //küldje el az üzenetet
		SimpleMailMessage message = null;  //üzenet, hogy sikeres a regiszt

		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(email);
			message.setSubject("Sikeres regisztrálás");
			message.setText("Kedves " + email + "! \n \n Köszönjük, hogy regisztráltál az oldalunkra!");//üzenet maga
			javaMailSender.send(message);//üzenet küldése

		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + email + "  " + e);
		}
	}
}

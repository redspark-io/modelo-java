package io.redspark.controller;

import javax.mail.MessagingException;

import io.redspark.security.Roles;
import io.redspark.service.SmtpMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-mail")
@Secured(Roles.ROLE_ADMIN)
public class MailController {

	@Autowired
	private SmtpMailSender mailSender;
	
	@Transactional
	@RequestMapping(method = RequestMethod.GET)
	public String sendMail(@RequestParam(value = "to", required = true) String to) throws MessagingException{
		
		mailSender.send(to, "Spring boot test mail", "Hello");
	}
	
}

package io.redspark.controller;

import io.redspark.security.Roles;
import io.redspark.service.SmtpMailSender;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

	@RequestMapping(method = RequestMethod.GET)
	public void sendMail(@RequestParam(value = "to", required = true) String to) throws MessagingException {

		mailSender.send(to, "Spring boot test mail", "Hello");
	}

}
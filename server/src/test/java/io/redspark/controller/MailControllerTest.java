package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static org.junit.Assert.assertEquals;
import io.redspark.ApplicationTest;
import io.redspark.domain.User;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public class MailControllerTest extends ApplicationTest{

	@Test
	public void sendMailTest(){
		
		User caio = admin("caio").build();
		saveall(caio);
		signIn(caio);
		
		Page<String> result = get("/send-mail").queryParam("to", "caio.ferreira@dclick.com.br").status(HttpStatus.OK).getPage(String.class);
		
		assertEquals(result.getContent(), "Enviou");
	}
}

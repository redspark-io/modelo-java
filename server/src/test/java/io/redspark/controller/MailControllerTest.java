package io.redspark.controller;

import static io.redspark.compose.Compose.admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.subethamail.wiser.Wiser;

import io.redspark.ApplicationTest;
import io.redspark.domain.User;

public class MailControllerTest extends ApplicationTest {

  private Wiser wiser;

  @Before
  public void config() {
    wiser = new Wiser(10002);
    wiser.start();
  }

  @After
  public void tearDown() throws Exception {
    wiser.stop();
  }

  @Test
  public void sendMailTest() {
    User caio = admin("caio").build();
    saveall(caio);
    signIn(caio);

    get("/send-mail").queryParam("to", "caio.ferreira@dclick.com.br").expectedStatus(HttpStatus.OK).getResponse();
  }
}
package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.controller.dto.UserDTO;
import io.redspark.domain.User;

public class LoginAndAccessTest extends ApplicationTest {

  @Test
  public void testLogin() throws Exception {
    User user = admin("bruno").build();
    saveall(user);

    ResponseEntity<UserDTO> response = post("/login").formParam("username", user.getLogin())
        .formParam("password", user.getPassword()).getResponse(UserDTO.class);

    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody().getName(), equalTo(user.getName()));
    assertThat(response.getBody().getId(), equalTo(user.getId()));
    assertThat(response.getBody().getAdmin(), equalTo(user.getAdmin()));

  }

  @Test
  public void testLoginFailure() {

    ResponseEntity<String> response = post("/login").formParam("username", "1").formParam("password", "2")
        .getResponse(String.class);

    assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
  }

  @Test
  public void testUnauthorized() {
    get("/me").expectedStatus(HttpStatus.UNAUTHORIZED).getResponse();
  }
}

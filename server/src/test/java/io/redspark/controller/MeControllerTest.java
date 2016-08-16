package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import io.redspark.SescApplicationTest;
import io.redspark.controller.dto.UserDTO;
import io.redspark.domain.User;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MeControllerTest extends SescApplicationTest {

	@Test
	public void testMe() {
		User user = admin("test").build();
		saveAll(user);
		signIn(user);

		ResponseEntity<UserDTO> response = get("/me")
		    .expectedStatus(HttpStatus.OK)
		    .getResponse(UserDTO.class);
		assertThat(response.getBody().getId(), equalTo(user.getId()));
	}

	@Test
	public void testMe401() {
		get("/me").expectedStatus(HttpStatus.UNAUTHORIZED).getResponse();
	}
}

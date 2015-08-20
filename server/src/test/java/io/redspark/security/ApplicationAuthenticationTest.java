package io.redspark.security;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import io.redspark.SescApplicationTest;
import io.redspark.controller.dto.UserDTO;
import io.redspark.security.sesc.SescApplicationUser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;

import br.org.sesc.commons.security.SescUser;

public class ApplicationAuthenticationTest extends SescApplicationTest {

	@Autowired
	private SescApplicationUser sescApplicationUser;

	@Test
	public void testAuthenticationSucessfully() {
		assertThat(sescApplicationUser.size(), greaterThan(0));

		SescUser appUser = sescApplicationUser.getSescUser(0);

		String authString = appUser.getLogin() + ":" + appUser.getPassword();
		byte[] authEncBytes = Base64.encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		ResponseEntity<UserDTO> response = get("/me")
		    .header("Authorization", "Basic " + authStringEnc)
		    .expectedStatus(HttpStatus.OK)
		    .getResponse(UserDTO.class);

		assertThat(response.getBody().getName(), is(appUser.getLogin()));

	}
}
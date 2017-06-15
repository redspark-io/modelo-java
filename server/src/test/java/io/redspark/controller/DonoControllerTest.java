package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.dono;
import static org.springframework.http.HttpStatus.OK;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.DonoDTO;
import io.redspark.domain.User;

public class DonoControllerTest extends ApplicationTest {

	@Before
	public void before() {

		User amind = admin("Naelson").build();
		saveall(amind);
		signIn(amind);

		save(dono("Amicão Vet", "ami@cao.com").build());
		save(dono("DogHouse Clinica Vet", "dog@house.com").build());
		save(dono("Zonacão", "zona@cao.com").build());
	}

	@Test
	public void find_shouldReturnDonos() {

		ResponseEntity<DonoDTO[]> response = get(ControllerConstants.DONO).expectedStatus(OK)
				.getResponse(DonoDTO[].class);

		Assert.assertThat(response.getBody().length, Matchers.equalTo(3));
	}
}

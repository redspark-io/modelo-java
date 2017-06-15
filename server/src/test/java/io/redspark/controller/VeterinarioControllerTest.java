package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.veterinario;
import static org.springframework.http.HttpStatus.OK;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.VeterinarioDTO;
import io.redspark.domain.User;

public class VeterinarioControllerTest extends ApplicationTest {

	@Before
	public void before() {

		User amind = admin("Naelson").build();
		saveall(amind);
		signIn(amind);

		save(veterinario("Amicão Vet").build());
		save(veterinario("DogHouse Clinica Vet").build());
		save(veterinario("Zonacão").build());
	}

	@Test
	public void find_shouldReturnVeterinarios() {

		ResponseEntity<VeterinarioDTO[]> response = get(ControllerConstants.VETERINARIO).expectedStatus(OK)
				.getResponse(VeterinarioDTO[].class);

		Assert.assertThat(response.getBody().length, Matchers.equalTo(3));
	}
}
